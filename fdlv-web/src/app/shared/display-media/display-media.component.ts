import { Component, Input, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MediaType } from 'src/app/enums/media-type';
import { MediasService } from 'src/app/services/forum/medias.service';
import { Subject, of } from 'rxjs';



@Component({
  selector: 'app-display-media',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './display-media.component.html',
  styleUrls: ['./display-media.component.scss']
})
export class DisplayMediaComponent {
  mediaType: MediaType = MediaType.IMAGE;
  @Input() url: string;
  error: boolean = false;
  @Input() file: File;

  /**
   * J'ai créé ce composant pour pouvoir afficher les médias présents sur le serveur.
   * Actuellement il ne marche pas car l'url du fichier n'est pas enregistrés avec l'extention sur le serveur.
   */
  constructor() { }

  ngOnChanges(change: SimpleChanges) {
    if (change['file']) {
      if (change['file'].currentValue.type.match('image/*')) this.mediaType = MediaType.IMAGE;
      else if (change['file'].currentValue.type.match('video/*')) this.mediaType = MediaType.VIDEO;
    }
    else if (change['url']) {
      if (change['url'].currentValue.match(/\.(jpg|jpeg|png|gif)$/i)) this.mediaType = MediaType.IMAGE;
      else if (change['url'].currentValue.match(/\.(mp4)$/i)) {
        this.mediaType = MediaType.VIDEO;
      }
      else this.error = true;
    } else this.error = true;
    
  }

  fileIsImage() {
    return this.mediaType == MediaType.IMAGE;
  }

  fileIsVideo() {
    return this.mediaType == MediaType.VIDEO;
  }
}
