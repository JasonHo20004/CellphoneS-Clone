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
import { OrderResponse } from '../../responses/order/order.response';
import { OrderService } from '../../services/order.service';
import { OrderDetail } from '../../models/order.detail';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterComponent],
  templateUrl: './order.detail.component.html',
  styleUrl: './order.detail.component.scss',
})
export class OrderDetailComponent implements OnInit {
  orderResponse: OrderResponse = {
    id: 1, // Hoặc bất kỳ giá trị số nào
    user_id: 0,
    fullname: '',
    phone_number: '',
    email: '',
    address: '',
    note: '',
    order_date: new Date(),
    status: '',
    total_money: 0, // Hoặc bất kỳ giá trị số nào
    shipping_method: '',
    shipping_address: '',
    shipping_date: new Date(),
    payment_method: '',
    order_details: [] // Một mảng rỗng
  };  

  cartItems: { product: Product; quantity: number }[] = [];
  couponCode: string = ''; // Mã giảm giá
  totalAmount: number = 0; // Tổng tiền

  constructor(
    private orderService: OrderService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails(): void {
    debugger
    const orderId = Number(this.route.snapshot.paramMap.get('orderId'));
    this.orderService.getOrderById(orderId).subscribe({
      next: (response: any) => {        
        debugger;       
        this.orderResponse.id = response.id;
        this.orderResponse.user_id = response.user_id;
        this.orderResponse.fullname = response.fullname;
        this.orderResponse.email = response.email;
        this.orderResponse.phone_number = response.phone_number;
        this.orderResponse.address = response.address; 
        this.orderResponse.note = response.note;
        this.orderResponse.order_date = new Date(
          response.order_date[0], 
          response.order_date[1] - 1, 
          response.order_date[2]
        );        
        
        this.orderResponse.order_details = response.order_details
          .map((order_detail: OrderDetail) => {
          order_detail.product.thumbnail = `${environment.apiBaseUrl}/products/images/${order_detail.product.thumbnail}`;
          return order_detail;
        });        
        this.orderResponse.payment_method = response.payment_method;
        this.orderResponse.shipping_date = new Date(
          response.shipping_date[0], 
          response.shipping_date[1] - 1, 
          response.shipping_date[2]
        );
        
        this.orderResponse.shipping_method = response.shipping_method;
        
        this.orderResponse.status = response.status;
        this.orderResponse.total_money = response.total_money;
      },
      complete: () => {
        debugger;        
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching detail:', error);
      }
    });
  }
  // ngOnInit(): void {
  //   const cart = this.cartService.getCart();
  //   const productIds = Array.from(cart.keys());

  //   if (productIds.length === 0) {
  //     this.cartItems = [];
  //     return;
  //   }

  //   this.productService.getProductsByIds(productIds).subscribe({
  //     next: (response: ApiResponse) => {
  //       // console.log('Full response:', response);
  //       // console.log('Response type:', typeof response);
  //       // console.log('Response keys:', Object.keys(response));
        
  //       // Ensure data is an array before processing
  //       const products: Product[] = Array.isArray(response) 
  //         ? response 
  //         : (response.data || []);

  //       this.cartItems = productIds.map((productId) => {
  //         const product = products.find((p) => p.productId === productId);
  //         if (product) {
  //           product.thumbnail = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
  //         }
  //         return {
  //           product: product!, 
  //           quantity: cart.get(productId)!
  //         };
  //       });

  //       this.calculateTotal();
  //     },
  //     error: (error: any) => {
  //       console.error('Error fetching product details:', error);
  //       this.cartItems = [];
  //     }
  //   });
  // }

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
