import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';
import { HttpUtilService } from './http.util.service';
import { UserResponse } from '../responses/user/user.response';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiUserDetail = `${environment.apiBaseUrl}/users/details`;

  private apiConfig = {
    headers: this.httpUtilService.createHeaders(),
  };

  constructor(
    private http: HttpClient,
    private httpUtilService: HttpUtilService,
    @Inject(PLATFORM_ID) private platformId: Object // Inject platform ID
  ) {}

  register(registerDTO: RegisterDTO): Observable<any> {
    return this.http.post(this.apiRegister, registerDTO, this.apiConfig);
  }

  login(loginDTO: LoginDTO): Observable<any> {    
    return this.http.post(this.apiLogin, loginDTO, this.apiConfig);
  }

  getUserDetail(token: string): Observable<UserResponse> {
    return this.http.post<UserResponse>(
      this.apiUserDetail,
      {}, // Ensure the body is an empty object if no payload is required
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        })
      }
    );
  }

  saveUserResponseToLocalStorage(userResponse?: UserResponse) {
    if (isPlatformBrowser(this.platformId)) {
      try {
        if (!userResponse) {
          return;
        }
        const userResponseJSON = JSON.stringify(userResponse);  
        localStorage.setItem('user', userResponseJSON);
        console.log('User response saved to local storage.');
      } catch (error) {
        console.error('Error saving user response to local storage:', error);
      }
    }
  }

  getUserResponseFromLocalStorage(): UserResponse | null {
    if (isPlatformBrowser(this.platformId)) {
      try {
        const userResponseJSON = localStorage.getItem('user');
        if (!userResponseJSON) {
          return null;
        }
        const userResponse = JSON.parse(userResponseJSON);
        console.log('User response retrieved from local storage.');
        return userResponse;
      } catch (error) {
        console.error('Error retrieving user response from local storage:', error);
        return null;
      }
    }
    return null;
  }

  removeUserFromLocalStorage(): void {
    if (isPlatformBrowser(this.platformId)) {
      try {
        localStorage.removeItem('user');
        console.log('User data removed from local storage.');
      } catch (error) {
        console.error('Error removing user data from local storage:', error);
      }
    }
  }
}
