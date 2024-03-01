import {ColumnDataInterface} from "../interfaces/column-data.interface";

export const EventDetailColumnData: ColumnDataInterface[] = [
  {
    header: 'ID',
    field: 'id'
  },
  {
    header: 'Titre',
    field: 'label'
  },
  {
    header: 'Description',
    field: 'description'
  },
  /*{
    header: 'Login de l\'utilisateur',
    field: 'label'
  },*/
  {
    header: 'Thème',
    field: 'theme.id'
  },
  {
    header: 'Numéro du Parcours',
    field: 'numParcours'
  },
  {
    header: 'Code Postal',
    field: 'zipCode'
  },
  {
    header: 'Ville',
    field: 'city'
  },
  {
    header: 'Date de début',
    field: 'startAt'
  },
  {
    header: 'Date de fin',
    field: 'endAt'
  },
  {
    header: 'Autre parcours',
    field: 'otherEvent'
  },

]
