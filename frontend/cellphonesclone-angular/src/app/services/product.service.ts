import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiGetProducts = `${environment.apiBaseUrl}/products`;

  constructor(private http: HttpClient) { }

  getProducts(
    keyword?: string, 
    brandId?: number, 
    page: number = 0, 
    limit: number = 10
  ): Observable<Product[]> {
    let params = new HttpParams();
    
    // Only add non-empty keyword
    if (keyword && keyword.trim() !== '') {
      params = params.set('keyword', keyword.trim());
    }
    
    // Only add non-zero brandId
    if (brandId && brandId !== 0) {
      params = params.set('brand_id', brandId.toString());
    }
    
    params = params
      .set('page', page.toString())
      .set('limit', limit.toString());
    
    return this.http.get<Product[]>(this.apiGetProducts, { params });
  }
}
