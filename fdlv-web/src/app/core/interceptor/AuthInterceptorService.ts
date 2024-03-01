import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {AuthService} from "../auth/auth.service";
import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService,private router:Router) {
  }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const id_token = this.authService.getToken();

    if (id_token) {
      request = request.clone({
        setHeaders: {Authorization: `Bearer ${id_token}`}
      });
      console.log(request);
    }

    return next.handle(request).pipe(
      catchError((err) => {
        console.log('error')
        if (err instanceof HttpErrorResponse) {
          if (err.status === 401) {
            this.router.navigate(['login'])
          }
        }
        return throwError(err);
      })
    )
  }
}
