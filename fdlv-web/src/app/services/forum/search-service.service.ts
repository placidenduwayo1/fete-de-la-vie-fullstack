import { Injectable } from '@angular/core'

@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {
  constructor () {}

  // Méthode pour sauvegarder la valeur de recherche
  saveSearchValue (searchValue: string): void {
    localStorage.setItem('searchValue', searchValue)
  }

  // Méthode pour récupérer la valeur de recherche
  getSearchValue (): string | null {
    return localStorage.getItem('searchValue')
  }
}
