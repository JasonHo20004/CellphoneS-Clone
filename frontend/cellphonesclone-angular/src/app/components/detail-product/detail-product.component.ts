import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { Product } from '../../models/product';
import { CommonModule } from '@angular/common';
import { ApiResponse } from '../../responses/api.response';
import { HttpErrorResponse } from '@angular/common/http';
import { BaseComponent } from '../base/base.component';
import { ProductService } from '../../services/product.service';
import { ProductImage } from '../../models/product.image';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-detail-product',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, CommonModule],
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.scss']
})
export class DetailProductComponent implements OnInit{
  product?: Product;
  productId: number = 0;
  loading: boolean = true;
  currentImageIndex: number = 0;
  quantity: number = 1;
  isPressedAddToCart: boolean = false;  
  error: string | null = null;
  
constructor(
  private productService: ProductService,
  private router: Router,
  private cartService: CartService,
  private activatedRoute: ActivatedRoute
) {}

  ngOnInit() {
    // Lấy productId từ URL      
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    debugger
    //this.cartService.clearCart();
    //const idParam = 9 //fake tạm 1 giá trị
    if (idParam !== null) {
      this.productId = +idParam;
    }
    if (!isNaN(this.productId)) {
      this.productService.getDetailProduct(this.productId).subscribe({
        next: (response: any) => {            
          // Lấy danh sách ảnh sản phẩm và thay đổi URL
          debugger
          if (response.product_images && response.product_images.length > 0) {
            response.product_images.forEach((product_image:ProductImage) => {
              product_image.imageUrl = `${environment.apiBaseUrl}/products/images/${product_image.imageUrl}`;
              debugger
            });
          }            
          debugger
          this.product = response 
          // Bắt đầu với ảnh đầu tiên
          this.showImage(0);
        },
        complete: () => {
          debugger;
        },
        error: (error: any) => {
          debugger;
          console.error('Error fetching detail:', error);
        }
      });    
    } else {
      console.error('Invalid productId:', idParam);
    }      
  }
  
  private fetchProductDetails(): void {
    this.loading = true;
    this.productService.getDetailProduct(this.productId).subscribe({
      next: (apiResponse: any) => {
        // console.log('Full API Response:', apiResponse);
  
        // Transform image URLs directly from the response
        if (apiResponse.product_images?.length) {
          apiResponse.product_images = apiResponse.product_images.map((image: any) => ({
            ...image, // Preserve all existing properties
            image_url: `${environment.apiBaseUrl}/products/images/${image.imageUrl}` // Correct property name
          }));
        }
  
        this.product = apiResponse;
        this.loading = false;
  
        // Show first image if available
        if (this.product?.product_images?.length) {
          this.showImage(0);
        }
      },
      error: (error: HttpErrorResponse) => {
        console.error('Error fetching product:', error);
        this.error = 'Failed to load product details';
        this.loading = false;
      }
    });
  }

  showImage(index: number): void {
    debugger
    if (this.product && this.product.product_images &&
      this.product.product_images.length > 0) {
      // Đảm bảo index nằm trong khoảng hợp lệ        
      if (index < 0) {
        index = 0;
      } else if (index >= this.product.product_images.length) {
        index = this.product.product_images.length - 1;
      }
      // Gán index hiện tại và cập nhật ảnh hiển thị
      this.currentImageIndex = index;
    }
  }
  thumbnailClick(index: number) {
    debugger
    // Gọi khi một thumbnail được bấm
    this.currentImageIndex = index; // Cập nhật currentImageIndex
  }
  nextImage(): void {
    debugger
    this.showImage(this.currentImageIndex + 1);
  }

  previousImage(): void {
    debugger
    this.showImage(this.currentImageIndex - 1);
  }
  addToCart(): void {
    debugger
    this.isPressedAddToCart = true;
    if (this.product) {
      this.cartService.addToCart(this.product.productId, this.quantity);
    } else {
      // Xử lý khi product là null
      console.error('Không thể thêm sản phẩm vào giỏ hàng vì product là null.');
    }
  }

  increaseQuantity(): void {
    debugger
    this.quantity++;
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }
  getTotalPrice(): number {
    if (this.product) {
      return this.product.price * this.quantity;
    }
    return 0;
  }
  buyNow(): void {
    if (this.isPressedAddToCart == false) {
      this.addToCart();
    }
    this.router.navigate(['/orders']);
  }
}
