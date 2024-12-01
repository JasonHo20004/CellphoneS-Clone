import { Component, ViewChild } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { NgForm, FormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { LoginDTO } from '../../dtos/user/login.dto';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FooterComponent, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;

  phoneNumber: string ='0961826305';
  password: string = 'abc123';

  onPhoneNumberChange() {
    console.log(`Phone typed: ${this.phoneNumber}`);
  }

  constructor(private router: Router, private userService: UserService) {

  }

  // login() {
  //   console.log('Login method called');
  //   const message = `phone: ${this.phoneNumber}` + `user_password: ${this.password}`

  //   const loginDTO: LoginDTO = {
      
  //     "phone_number": this.phoneNumber,
  //     "password": this.password,
  //   };
  //   this.userService.login(loginDTO).subscribe({
  //     next: (response: any) => {
  //       if (response && (response.status === 200 || response.status === 201)) {
  //         //this.router.navigate(['/login']);
  //       }
  //     },
  //     error: (error: any) => {
  //       alert(`Cannot login, error: ${error.error}`);
  //     }
  //   });
  // }

  login() {
    console.log('Login method called');
    console.log(`Phone: ${this.phoneNumber}, Password: ${this.password}`);
  
    const loginDTO: LoginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
    };
  
    console.log('Attempting to login with DTO:', loginDTO);
  
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        const {token} = response.token
        console.log('Login response:', response);
        if (response && (response.status === 200 || response.status === 201)) {
          // Navigation or further actions
          console.log('Login successful');
        }
      },
      error: (error: any) => {
        console.error('Login error:', error);
        alert(`Cannot login, error: ${error.error}`);
      }
    });
  }
}
