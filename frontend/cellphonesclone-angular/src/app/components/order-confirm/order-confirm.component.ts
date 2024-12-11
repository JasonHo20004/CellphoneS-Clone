import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { Product } from '../../models/product';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { environment } from '../../environments/environment';
import { CommonModule } from '@angular/common';
import { ApiResponse } from '../../responses/api.response';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-order-confirm',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, CommonModule, FormsModule],
  templateUrl: './order-confirm.component.html',
  styleUrl: './order-confirm.component.scss',
})
export class OrderConfirmComponent implements OnInit {
  cartItems: { product: Product; quantity: number }[] = [];
  couponCode: string = ''; // Mã giảm giá
  totalAmount: number = 0; // Tổng tiền

  constructor(
    private cartService: CartService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    const cart = this.cartService.getCart();
    const productIds = Array.from(cart.keys());

    if (productIds.length === 0) {
      this.cartItems = [];
      return;
    }

    this.productService.getProductsByIds(productIds).subscribe({
      next: (response: ApiResponse) => {
        // console.log('Full response:', response);
        // console.log('Response type:', typeof response);
        // console.log('Response keys:', Object.keys(response));
        
        // Ensure data is an array before processing
        const products: Product[] = Array.isArray(response) 
          ? response 
          : (response.data || []);

        this.cartItems = productIds.map((productId) => {
          const product = products.find((p) => p.productId === productId);
          if (product) {
            product.thumbnail = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
          }
          return {
            product: product!, 
            quantity: cart.get(productId)!
          };
        });

        this.calculateTotal();
      },
      error: (error: any) => {
        console.error('Error fetching product details:', error);
        this.cartItems = [];
      }
    });
  }

  // Hàm tính tổng tiền
  calculateTotal(): void {
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0
    );
  }

  // Hàm xử lý việc áp dụng mã giảm giá
  applyCoupon(): void {
    // Viết mã xử lý áp dụng mã giảm giá ở đây
    // Cập nhật giá trị totalAmount dựa trên mã giảm giá nếu áp dụng
  }
}
