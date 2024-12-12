import { ApplicationConfig, Provider } from '@angular/core';
import { provideRouter, RouterModule } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { withFetch } from '@angular/common/http';



import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { TokenInterceptor } from './interceptors/token.interceptor';

const tokenInterceptorProvider: Provider =
  { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true };


export const appConfig: ApplicationConfig = {
  providers: [
    // provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes), 
    //importProvidersFrom(RouterModule.forRoot(routes)),
    // importProvidersFrom(RouterModule.forChild(adminRoutes)),    
    provideHttpClient(withFetch()),
    //provideHttpClient(),
    tokenInterceptorProvider,
    provideClientHydration(),
    //importProvidersFrom(HttpClientModule),
  ]
};