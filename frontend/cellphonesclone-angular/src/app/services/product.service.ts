import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Product } from '../models/product';
import { ApiResponse } from '../responses/api.response';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiGetProducts = `${environment.apiBaseUrl}/products`;
  private apiBaseUrl = environment.apiBaseUrl;

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

  getDetailProduct(productId: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiBaseUrl}/products/${productId}`);
  }

  getProductsByIds(productIds: number[]): Observable<ApiResponse> {
    const params = new HttpParams().set('ids', productIds.join(','));
    return this.http.get<ApiResponse>(`${this.apiBaseUrl}/products/by-ids`, { params });
  }
  deleteProduct(productId: number): Observable<ApiResponse> {
    debugger
    return this.http.delete<ApiResponse>(`${this.apiBaseUrl}/products/${productId}`);
  }
  // updateProduct(productId: number, updatedProduct: UpdateProductDTO): Observable<ApiResponse> {
  //   return this.http.put<ApiResponse>(`${this.apiBaseUrl}/products/${productId}`, updatedProduct);
  // }  
  // insertProduct(insertProductDTO: InsertProductDTO): Observable<ApiResponse> {
  //   // Add a new product
  //   return this.http.post<ApiResponse>(`${this.apiBaseUrl}/products`, insertProductDTO);
  // }
  uploadImages(productId: number, files: File[]): Observable<ApiResponse> {
    const formData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }
    // Upload images for the specified product id
    return this.http.post<ApiResponse>(`${this.apiBaseUrl}/products/uploads/${productId}`, formData);
  }
  deleteProductImage(id: number): Observable<any> {
    debugger
    return this.http.delete<string>(`${this.apiBaseUrl}/product_images/${id}`);
  }
}
