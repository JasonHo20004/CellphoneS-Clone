import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Brand } from '../models/brand';
@Injectable({
  providedIn: 'root'
})
export class BrandService {
  private apiGetBrands  = `${environment.apiBaseUrl}/brands`;

  constructor(private http: HttpClient) { }
  getBrands(page: number, limit: number):Observable<Brand[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());     
      return this.http.get<Brand[]>(this.apiGetBrands, { params });           
  }
}
