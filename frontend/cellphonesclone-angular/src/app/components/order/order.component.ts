import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { environment } from '../../environments/environment';
import { Product } from '../../models/product';
import { ApiResponse } from '../../responses/api.response';
import { BaseComponent } from '../base/base.component';
import { OrderDTO } from '../../dtos/order/order.dto';
import { CartService } from '../../services/cart.service';
import { HttpErrorResponse } from '@angular/common/http';
import { OrderService } from '../../services/order.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent{
  // private formBuilder = inject(FormBuilder);  

  // orderForm: FormGroup; // Đối tượng FormGroup để quản lý dữ liệu của form
  cartItems: { product: Product, quantity: number }[] = [];
  totalAmount: number = 0; // Tổng tiền
  couponDiscount: number = 0; //số tiền được discount từ coupon
  couponApplied: boolean = false;
  cart: Map<number, number> = new Map();
  orderData: OrderDTO = {
    user_id: 0, // Thay bằng user_id thích hợp
    fullname: '', // Khởi tạo rỗng, sẽ được điền từ form
    email: '', // Khởi tạo rỗng, sẽ được điền từ form    
    phone_number: '', // Khởi tạo rỗng, sẽ được điền từ form
    address: '', // Khởi tạo rỗng, sẽ được điền từ form
    status: 'pending',
    note: '', // Có thể thêm trường ghi chú nếu cần
    total_money: 0, // Sẽ được tính toán dựa trên giỏ hàng và mã giảm giá
    payment_method: 'cod', // Mặc định là thanh toán khi nhận hàng (COD)
    shipping_method: 'express', // Mặc định là vận chuyển nhanh (Express)
    coupon_code: '', // Sẽ được điền từ form khi áp dụng mã giảm giá
    cart_items: []
  };

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private orderService: OrderService
  ) {}

  // constructor(private cartService: CartService, private orderService: OrderService) {
  //   // super();
  //   // // Tạo FormGroup và các FormControl tương ứng
  //   // this.orderForm = this.formBuilder.group({
  //   //   fullname: ['', Validators.required], // fullname là FormControl bắt buộc      
  //   //   email: ['', [Validators.email]], // Sử dụng Validators.email cho kiểm tra định dạng email
  //   //   phone_number: ['', [Validators.required, Validators.minLength(6)]], // phone_number bắt buộc và ít nhất 6 ký tự
  //   //   address: ['', [Validators.required, Validators.minLength(5)]], // address bắt buộc và ít nhất 5 ký tự
  //   //   note: [''],
  //   //   couponCode: [''],
  //   //   shipping_method: ['express'],
  //   //   payment_method: ['cod']
  //   // });
  // }
  
  ngOnInit(): void {  
    debugger
    // Ensure cart is refreshed
    this.cartService.refreshCart();
  
    const cart = this.cartService.getCart();
    console.log('Cart contents (keys):', Array.from(cart.keys())); 
  
    const productIds = Array.from(cart.keys());    
    console.log('Product IDs in cart:', productIds);
  
    if(productIds.length === 0) {
      console.warn('Cart is empty');
      return;
    }    
  
    this.productService.getProductsByIds(productIds).subscribe({
      next: (apiResponse: ApiResponse) => {            
        debugger
        const products: Product[] = apiResponse.data;
        console.log('Fetched products (IDs):', products.map(p => p.productId));
        
        this.cartItems = productIds.map((productId) => {
          debugger
          // Use strict equality checking
          const product = products.find((p) => p.productId === productId);
          const quantity = cart.get(productId);
          
          console.log(`Searching for Product ID: ${productId}, Type: ${typeof productId}`);
          console.log(`Quantity from cart: ${quantity}`);
          console.log('Matching product:', product);
  
          if (!product) {
            console.error(`Product not found for ID: ${productId}`);
            throw new Error(`Product with ID ${productId} not found`);
          }
  
          if (quantity === undefined) {
            console.error(`Quantity undefined for product ID: ${productId}`);
            throw new Error(`Quantity undefined for product ID: ${productId}`);
          }
  
          // Ensure thumbnail is fully qualified
          product.thumbnail = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
          
          return {
            product: product,
            quantity: quantity
          };
        });
        console.log('Final Cart Items:', this.cartItems);
        this.calculateTotal();
      },
      error: (error) => {
        console.error('Error processing cart items:', error);
      }
    });        
  }
  placeOrder() {
    debugger
    this.orderService.placeOrder(this.orderData).subscribe({
      next: (response) => {            
        debugger                
        console.log('Đặt hàng thành công');
      },
      complete: () => {
        debugger;
        this.calculateTotal()
      },
      error: (error: any) => {
        debugger;
        console.error('Lỗi khi đặt hàng:', error);
      }
    });        
  }
    
  decreaseQuantity(index: number): void {
    if (this.cartItems[index].quantity > 1) {
      this.cartItems[index].quantity--;
      // Cập nhật lại this.cart từ this.cartItems
      this.updateCartFromCartItems();
      this.calculateTotal();
    }
  }
  
  increaseQuantity(index: number): void {
    this.cartItems[index].quantity++;   
    // Cập nhật lại this.cart từ this.cartItems
    this.updateCartFromCartItems();
    this.calculateTotal();
  }    
  
  // Hàm tính tổng tiền
  calculateTotal(): void {
      this.totalAmount = this.cartItems.reduce(
          (total, item) => total + item.product.price * item.quantity,
          0
      );
  }
  confirmDelete(index: number): void {
    if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
      // Xóa sản phẩm khỏi danh sách cartItems
      this.cartItems.splice(index, 1);
      // Cập nhật lại this.cart từ this.cartItems
      this.updateCartFromCartItems();
      // Tính toán lại tổng tiền
      this.calculateTotal();
    }
  }
  // Hàm xử lý việc áp dụng mã giảm giá
  applyCoupon(): void {
    // debugger
    // const couponCode = this.orderForm.get('couponCode')!.value;
    // if (!this.couponApplied && couponCode) {
    //   this.calculateTotal();
    //   this.couponService.calculateCouponValue(couponCode, this.totalAmount)
    //     .subscribe({
    //       next: (apiResponse: ApiResponse) => {
    //         this.totalAmount = apiResponse.data;
    //         this.couponApplied = true;
    //       }
    //     });
    // }
  }
  private updateCartFromCartItems(): void {
    this.cart.clear();
    this.cartItems.forEach((item) => {
      this.cart.set(item.product.productId, item.quantity);
    });
    this.cartService.setCart(this.cart);
  }
}
