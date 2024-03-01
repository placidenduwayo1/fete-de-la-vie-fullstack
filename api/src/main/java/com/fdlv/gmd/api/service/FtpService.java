package com.fdlv.gmd.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Classe de service permettant de se connecter au serveur FTP et d'y déposer ou
 * supprimer des images
 */
@Service
public class FtpService {

	private final Logger log = LoggerFactory.getLogger(FtpService.class);

	@Value("${ftp.url}")
	private String ftpUrl;

	@Value("${ftp.port}")
	private Integer ftpPort;

	@Value("${ftp.user}")
	private String ftpUser;

	@Value("${ftp.password}")
	private String ftpPassword;

	@Value("${ftp.paths.root}")
	private String rootPath;

	@Value("${ftp.paths.testimony}")
	private String testimonyPath;

	@Value("${ftp.paths.info}")
	private String infoPath;

	@Value("${ftp.readUrl}")
	private String returnUrl;

	@Value("${ftp.paths.partenaire}")
	private String partenairePath;

	@Value("${ftp.paths.logo-path}")
	private String logoPath;

	@Value("${ftp.paths.teaser-path}")
	private String teaserPath;

	@Value("${ftp.paths.video-liste-videos-path}")
	private String videoListeVideosPath;

	@Value("${ftp.paths.audio-liste-videos-path}")
	private String audioListeVideosPath;

	@Value("${ftp.paths.pdf-liste-videos-path}")
	private String pdfListeVideosPath;

	@Value("${ftp.paths.image-liste-videos-path}")
	private String imageListeVideosPath;

	@Value("${ftp.paths.default-video-path}")
	private String defaultVideoPath;
	@Value("${ftp.paths.pdf-fin-parcours-path}")
	private String pdfFinParcoursPath;

	@Value("${ftp.paths.acteur-photo-path}")
	private String acteurPhotoPath;

	public String uploadFileToFTPGivenFullPath(MultipartFile file, String pathOnFtp, String nameOnServer) {

		FTPClient client = null;

		String extension = !StringUtils.isEmpty(file.getOriginalFilename()) ? "."+ FileNameUtils.getExtension(file.getOriginalFilename()):"";


		try {

			client = this.connectToFTP();

			final InputStream inputStream = file.getInputStream();

			this.log.debug("Start uploading first file");
			final boolean deleteOld = client.deleteFile(this.rootPath + pathOnFtp + nameOnServer+ extension);
			this.log.debug("Old file was overwritten file? {} ", deleteOld);
			// todo: if it is required to not overwrite old file, we can change logic here
			// maybe? or return an error?

			final boolean done = client.storeFile(this.rootPath + pathOnFtp + nameOnServer+ extension, inputStream);
			inputStream.close();
			if (done) {
				this.log.debug("The file {0} is uploaded successfully.", pathOnFtp + nameOnServer);
			}
		} catch (final IOException e) {
			this.log.error("error sending file to FTP server", e);
		} finally {
			this.disconnectFTP(client);
		}

		return this.returnUrl + pathOnFtp + nameOnServer + extension;
	}

	// new method to handle file upload
	public String uploadFileToFTP(String name, String type, MultipartFile file, String pathOnFtp) {
		final String nameWithoutExtension = StringUtils.substringBeforeLast(name, ".");
		final String sansAccents = StringUtils.stripAccents(nameWithoutExtension);
		// String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new
		// Date());
		final String nameOnServer = RegExUtils.replacePattern(sansAccents, "[^a-zA-Z0-9]", "_") + /*
																								   * "." + timestamp +
																								   */ "." + type;

		return this.uploadFileToFTPGivenFullPath(file, pathOnFtp, nameOnServer);
	}

	public boolean deleteFromServer(String readUrl) {
		boolean isDeleted = false;
		final String nameOnServer = readUrl.replace(this.returnUrl, this.rootPath);
		FTPClient client = null;

		try {

			client = this.connectToFTP();

			isDeleted = client.deleteFile(nameOnServer);
		} catch (final IOException e) {
			this.log.error("error deleting file from FTP server", e);
		} finally {
			this.disconnectFTP(client);
		}

		return isDeleted;
	}

	public FTPClient connectToFTP() {
		final FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(this.ftpUrl, this.ftpPort);
			ftpClient.login(this.ftpUser, this.ftpPassword);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (final IOException e) {
			this.log.error("error opening ftp link", e);
		}

		return ftpClient;
	}

	public void disconnectFTP(FTPClient client) {
		try {
			if (client != null && client.isConnected()) {
				client.logout();
				client.disconnect();
			}
		} catch (final IOException e) {
			this.log.error("error disconnecting ftp link", e);
		}
	}

	// updated methods
	public String putImageFile(String name, String type, MultipartFile image, boolean isTestimonyImage) {
		final String pathOnFtp = (isTestimonyImage ? this.testimonyPath : this.infoPath);
		return this.uploadFileToFTP(name, type, image, pathOnFtp);
	}

	public String putLogoTeaser(MultipartFile media, String typeMedia) {
		final String name = media.getOriginalFilename();
		final String type = FileNameUtils.getExtension(media.getOriginalFilename());
		String pathOnFtp = (Objects.equals(typeMedia, "Logo") ? this.logoPath : this.getPathForTeaser(type));
		// Todo: temporary - make a new service on the backend to save the pdf fin
		// parcours
		if (Objects.equals(typeMedia, "DocumentFinParcours")) {
			pathOnFtp = this.pdfFinParcoursPath;
		}
		return this.uploadFileToFTP(name, type, media, pathOnFtp);
	}

	public String putActeurPhoto(MultipartFile file, String fileName) {
		final String type = FileNameUtils.getExtension(file.getOriginalFilename());
		return this.uploadFileToFTP(fileName, type, file, this.acteurPhotoPath);
	}

	public String putMediaListeVideo(MultipartFile media) {
		final String name = media.getOriginalFilename();
		final String type = FileNameUtils.getExtension(media.getOriginalFilename());

		final String pathOnFtp = this.getPathForMedia(type);

		return this.uploadFileToFTP(name, type, media, pathOnFtp);
	}

	private String getPathForMedia(String type) {
		switch (type.toLowerCase()) {
			case "mp3":
				return this.audioListeVideosPath;
			case "mp4":
				return this.videoListeVideosPath;
			case "jpg":
			case "jpeg":
			case "png":
				return this.imageListeVideosPath;
			case "pdf":
				return this.pdfListeVideosPath;
			default:
				return this.defaultVideoPath;
		}
	}

	private String getPathForTeaser(String type) {
		switch (type.toLowerCase()) {
			case "mp3":
				return this.audioListeVideosPath;
			case "mp4":
				return this.teaserPath;
			case "jpg":
			case "jpeg":
			case "png":
				return this.logoPath;
			case "pdf":
				return this.pdfListeVideosPath;
			default:
				return this.defaultVideoPath;
		}
	}

	public String putThumbnailListeVideo(MultipartFile video) {
		final String pathOnFtp = this.imageListeVideosPath;
		final String name = video.getOriginalFilename();
		final String type = FileNameUtils.getExtension(video.getOriginalFilename());
		return this.uploadFileToFTP(name, type, video, pathOnFtp);
	}

	public String putLogoPartenaire(String type, MultipartFile logo) {
		final String name = logo.getOriginalFilename();
		final String pathOnFtp = this.partenairePath;
		return this.uploadFileToFTP(name, type, logo, pathOnFtp);
	}

	public void renameFile(String originalPath, String desiredPath) {
		FTPClient client = null;
		try {
			client = this.connectToFTP();
			final boolean success = client.rename(this.rootPath + originalPath, this.rootPath + desiredPath);
			if (success) {
				this.log.debug("The file {} has been renamed to {}", originalPath, desiredPath);
			}
		} catch (final IOException e) {
			this.log.error("Error renaming file on FTP server", e);
		} finally {
			this.disconnectFTP(client);
		}
	}
}
