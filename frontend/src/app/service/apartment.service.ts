import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApartmentDetails} from '../model/apartment-details';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Page} from '../model/page';
import {Apartment} from '../model/apartment';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ApartmentCard} from '../model/apartment-card';

@Injectable({
  providedIn: 'root'
})
export class ApartmentService {

  readonly baseUrl = '/api/apartments';
  readonly adminBaseUrl = '/api/admin/apartments';

  constructor(private http: HttpClient) {
  }

  public getApartmentsPage(queryParams): Observable<Page<ApartmentCard>> {
    return this.http.get<any>(`${this.baseUrl}`, {params: queryParams}).pipe(
      map(data => this.createPage(data))
    );
  }

  public getMyListingsPage(queryParams): Observable<Page<ApartmentCard>> {
    return this.http.get<any>(`${this.adminBaseUrl}`, {params: queryParams}).pipe(
      map(data => this.createPage(data))
    );
  }

  public getApartmentDetails(apartmentId: number): Observable<ApartmentDetails> {
    return this.http.get<ApartmentDetails>(`${this.adminBaseUrl}/${apartmentId}`).pipe(
      map(apartmentDetails => this.mapApartmentDetails(apartmentDetails))
    );
  }

  public getActiveApartmentDetails(apartmentId: number): Observable<ApartmentDetails> {
    return this.http.get<ApartmentDetails>(`${this.baseUrl}/${apartmentId}`).pipe(
      map(apartmentDetails => this.mapApartmentDetails(apartmentDetails))
    );
  }

  public addOrUpdateApartment(editMode: boolean, formData: FormData): Observable<any> {
    if (editMode) {
      return this.updateApartment(formData);
    } else {
      return this.addApartment(formData);
    }
  }

  private addApartment(formData: FormData): Observable<any> {
    return this.http.post(`${this.adminBaseUrl}`, formData);
  }

  private updateApartment(formData: FormData): Observable<Apartment> {
    return this.http.put<Apartment>(`${this.adminBaseUrl}`, formData);
  }

  public getApartment(apartmentId: number) {
    return this.http.get<Apartment>(`${this.adminBaseUrl}/${apartmentId}/edit`);
  }

  public enableApartment(apartmentId: number, enable: boolean) {
    return this.http.put<void>(`${this.adminBaseUrl}/${apartmentId}/enable`, {enable});
  }

  public deleteApartment(apartmentId: number) {
    return this.http.delete<any>(`${this.adminBaseUrl}/${apartmentId}`);
  }

  private mapApartmentDetails(apartmentDetails: ApartmentDetails) {
    if (!apartmentDetails.apartment.owner.profilePicture) {
      apartmentDetails.apartment.owner.profilePicture = '../../assets/default-pictures/default-profile-pic.jpg';
    }
    if (!apartmentDetails.images.length) {
      apartmentDetails.images.push('../../assets/default-pictures/no_images_details_view.png');
    }
    return apartmentDetails;
  }

  createPage(data) {
    const page = new Page<ApartmentCard>();
    page.content = data.content;
    page.content.map(apartment => {
      if (apartment.dateAvailable) {
        apartment.dateAvailable = new Date(apartment.dateAvailable);
      }
      apartment.datePosted = new Date(apartment.datePosted);
      if (!apartment.mainImage) {
        apartment.mainImage = '../../assets/default-pictures/no_images_details_view.png';
      }
      return apartment;
    });
    page.pageNumber = data.pageable.pageNumber + 1;
    page.total = data.totalElements;
    page.pageSize = data.pageable.pageSize;
    return page;
  }

  initialiseApartmentForm(editMode: boolean): FormGroup {
    return new FormGroup({
      id: new FormControl({value: null, disabled: !editMode}),
      owner: new FormControl({value: null, disabled: !editMode}),
      title: new FormControl(null, Validators.required),
      city: new FormControl({value: 'Skopje', disabled: editMode}),
      municipality: new FormControl({value: null, disabled: editMode}, Validators.required),
      address: new FormControl(null, Validators.required),
      floor: new FormControl(null, Validators.min(0)),
      dateAvailable: new FormControl(),
      area: new FormControl(null, Validators.min(1)),
      price: new FormControl(null, [Validators.required, Validators.min(1)]),
      numberBedrooms: new FormControl(null, Validators.required),
      numberTenants: new FormControl(null, Validators.min(1)),
      description: new FormControl(),
      heating: new FormControl(false),
      parking: new FormControl(false),
      balcony: new FormControl(false),
      elevator: new FormControl(false)
    });
  }
}
