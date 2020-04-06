import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Statistics} from '../model/statistics';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private readonly statisticsUrl = '/api/statistics';

  constructor(private http: HttpClient) {
  }

  public getStatistics(): Observable<Statistics[]> {
    return this.http.get<Statistics[]>(this.statisticsUrl);
  }
}
