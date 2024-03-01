import { ForumTheme } from "./forum-theme.model"

export class Banniere {
  id: number;
  //new modif debut
  forumThemeId: number; // forum them id
  //ban_actif: boolean;
  //new modif fin
  libelle: string;
  code: string;
  //themeForum: number
  url: string;
  fdlv: string;
  commentaire: string;
}