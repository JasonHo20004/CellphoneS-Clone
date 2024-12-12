// import { NgModule } from "@angular/core";
// import { HomeComponent } from "./components/home/home.component";
// import { HeaderComponent } from "./components/header/header.component";
// import { FooterComponent } from "./components/footer/footer.component";
// import { DetailProductComponent } from "./components/detail-product/detail-product.component";
// import { OrderComponent } from "./components/order/order.component";
// import { OrderDetailComponent } from "./components/order-detail/order.detail.component";
// import { LoginComponent } from "./components/login/login.component";
// import { RegisterComponent } from "./components/register/register.component";
// import { AppComponent } from "./components/app/app.component";
// import { FormsModule, ReactiveFormsModule } from "@angular/forms";
// import { BrowserModule } from "@angular/platform-browser";
// import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from "@angular/common/http";
// import { AppRoutingModule } from "./app-routing.module";
// import { TokenInterceptor } from "./interceptors/token.interceptor";
// import { CommonModule } from "@angular/common";

// @NgModule({
//   declarations: [    
//     HomeComponent, 
//     HeaderComponent,
//     FooterComponent, 
//     DetailProductComponent, 
//     OrderComponent, 
//     OrderDetailComponent, 
//     LoginComponent, 
//     RegisterComponent, 
//     AppComponent
//   ],
//   imports: [
//     BrowserModule,
//     CommonModule,
//     FormsModule,
//     ReactiveFormsModule,
//     HttpClientModule,
//     AppRoutingModule,
//   ],
//   providers: [
//     {
//       provide: HTTP_INTERCEPTORS,
//       useClass: TokenInterceptor,
//       multi: true,
//     },
//   ],
//   bootstrap: [AppComponent]
// })
// export class AppModule { }