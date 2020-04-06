import {Account} from './account';

export class Apartment {
  id: number;
  title: string;
  city: string;
  municipality: string;
  address: string;
  datePosted: Date;
  dateAvailable: Date;
  price: number;
  area: number;
  description: string;
  numberBedrooms: number;
  numberTenants: number;
  floor: number;
  heating: boolean;
  parking: boolean;
  balcony: boolean;
  elevator: boolean;
  active: boolean;
  owner: Account;
  mainImage: string;
}
