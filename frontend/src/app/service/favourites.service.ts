import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Page} from '../model/page';
import {map} from 'rxjs/operators';
import {ApartmentService} from './apartment.service';
import {ApartmentCard} from '../model/apartment-card';

@Injectable({
  providedIn: 'root'
})
export class FavouritesService {

  readonly baseUrl = '/api/favourites';

  constructor(private http: HttpClient, private apartmentService: ApartmentService) {
  }

  getFavourites(): Observable<number[]> {
    return this.http.get<number[]>(this.baseUrl);
  }

  addToFavourites(apartmentId) {
    return this.http.post(`${this.baseUrl}/${apartmentId}`, null);
  }

  deleteFromFavourites(apartmentId) {
    return this.http.delete(`${this.baseUrl}/${apartmentId}`);
  }

  getFavouriteApartments(options): Observable<Page<ApartmentCard>> {
    return this.http.get<any>(`${this.baseUrl}/apartments`, {params: {...options}})
      .pipe(map(data => this.apartmentService.createPage(data)));
  }
}
