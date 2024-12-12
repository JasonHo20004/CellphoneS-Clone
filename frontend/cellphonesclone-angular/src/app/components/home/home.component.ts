import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { Product } from '../../models/product';
import { Brand } from '../../models/brand';
import { ProductService } from '../../services/product.service';
import { BrandService } from '../../services/brand.service';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, FormsModule, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})

export class HomeComponent implements OnInit {
  products: Product[] = [];
  brands: Brand[] = []; // Dữ liệu động từ brandService
  selectedBrandId: number  = 0; // Giá trị brand được chọn
  currentPage: number = 1;
  itemsPerPage: number = 12;
  pages: number[] = [];
  totalPages:number = 0;
  visiblePages: number[] = [];
  keyword:string = "";

  constructor(
    private productService: ProductService,
    private brandService: BrandService,    
    private router: Router
    ) {}

  ngOnInit() {
    this.getProducts(this.keyword, this.selectedBrandId, this.currentPage, this.itemsPerPage);
    this.getBrands(1, 100);
  }
  getBrands(page: number, limit: number) {
    this.brandService.getBrands(page, limit).subscribe({
      next: (brands: Brand[]) => {
        debugger
        this.brands = brands;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        console.error('Error fetching brands:', error);
      }
    });
  }
  searchProducts() {
    this.currentPage = 0;
    this.itemsPerPage = 12;
    debugger
    this.getProducts(this.keyword, this.selectedBrandId, this.currentPage, this.itemsPerPage);
  }
  getProducts(keyword: string, selectedBrandId: number, page: number, limit: number) {
    debugger
    this.productService.getProducts(keyword, selectedBrandId, page, limit).subscribe({
      next: (response: any) => {
        debugger
        // console.log('Products:', response.products);
        response.products.forEach((product: Product) => {      
          product.url = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
        });
        this.products = response.products;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching products:', error);
      }
    });    
  }
  onPageChange(page: number) {
    debugger;
    this.currentPage = page;
    this.getProducts(this.keyword, this.selectedBrandId, this.currentPage, this.itemsPerPage);
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 1);
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 1);
    }

    return new Array(endPage - startPage + 1).fill(0).map((_, index) => startPage + index);
  }
  // Hàm xử lý sự kiện khi sản phẩm được bấm vào
  onProductClick(productId: number) {
    debugger
    // Điều hướng đến trang detail-product với productId là tham số
    this.router.navigate(['/products', productId]);
  }
}
