import { CommonModule } from '@angular/common'
import { Component, Input, Output, EventEmitter } from '@angular/core'
import { FormsModule } from '@angular/forms'

@Component({
  selector: 'app-time-picker',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './time-picker.component.html',
  styleUrls: ['./time-picker.component.scss']
})
export class TimePickerComponent {
  @Input() time: string
  @Output() timeChange = new EventEmitter<string>()

  // Ajoutez la logique de gestion de l'heure ici
  public selectedHour: string = '09'
  public selectedMinute: string = '00'
  public selectedSecond: string = '00'

  // Créez des tableaux d'options pour les heures, minutes et secondes
  public hours: string[] = Array.from(
    { length: 24 },
    (_, i) => (i < 10 ? '0' : '') + i.toString()
  )
  public minutes: string[] = Array.from(
    { length: 60 },
    (_, i) => (i < 10 ? '0' : '') + i.toString()
  )
  public seconds: string[] = Array.from(
    { length: 60 },
    (_, i) => (i < 10 ? '0' : '') + i.toString()
  )

  // Méthode pour mettre à jour le temps
  updateTime (): void {
    const updatedTime = `${this.selectedHour}:${this.selectedMinute}:${this.selectedSecond}`
    this.timeChange.emit(updatedTime)
  }
}
