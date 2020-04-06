import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AbstractControl, AsyncValidatorFn, FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable, of, throwError} from 'rxjs';
import {catchError, flatMap, map} from 'rxjs/operators';
import {Account} from '../model/account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) {
  }

  private readonly baseURL = '/api/accounts';
  private readonly usernameRegex = '^[\.a-z0-9_-]*$';
  private readonly passwordRegex = '^(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=\\D*\\d)[A-Za-z\\d!$%@#£€*?&]{8,}$';
  private readonly phoneRegex = '^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$';

  static checkPasswordMatch(control: AbstractControl) {
    const password = control.get('password').value;
    const confirmPassword = control.get('confirmPassword').value;
    if (password !== confirmPassword) {
      control.get('confirmPassword').setErrors({confirmFail: true});
    } else {
      return null;
    }
  }

  initializeSignUpForm(): FormGroup {
    return new FormGroup({
      username: new FormControl(null, {
        validators: [Validators.required, Validators.minLength(3),
          Validators.maxLength(15), Validators.pattern(this.usernameRegex)],
        asyncValidators: this.checkUsernameAvailable.bind(this),
        updateOn: 'blur'
      }),
      password: new FormControl(null, {
        validators: [Validators.required, Validators.pattern(this.passwordRegex)],
        updateOn: 'blur'
      }),
      confirmPassword: new FormControl(null, Validators.required),
      firstName: new FormControl(null, {
        validators: Validators.required,
        updateOn: 'blur'
      }),
      lastName: new FormControl(null, {
        validators: Validators.required,
        updateOn: 'blur'
      }),
      email: new FormControl(null, {
        validators: [Validators.required, Validators.email],
        asyncValidators: this.checkEmailAvailable(null).bind(this),
        updateOn: 'blur'
      }),
      phone: new FormControl(null, {
        validators: [Validators.required, Validators.pattern(this.phoneRegex)],
        updateOn: 'blur'
      }),
      city: new FormControl(null),
      country: new FormControl(null)
    }, {
      validators: AccountService.checkPasswordMatch.bind(this),
      updateOn: 'blur'
    });
  }

  initializeEditForm(currentEmail: string) {
    return new FormGroup({
      firstName: new FormControl(null, {
        validators: Validators.required,
        updateOn: 'blur'
      }),
      lastName: new FormControl(null, {
        validators: Validators.required,
        updateOn: 'blur'
      }),
      email: new FormControl(null, {
        validators: [Validators.required, Validators.email],
        asyncValidators: this.checkEmailAvailable(currentEmail).bind(this),
        updateOn: 'blur'
      }),
      phone: new FormControl(null, {
        validators: [Validators.required, Validators.pattern(this.phoneRegex)],
        updateOn: 'blur'
      }),
      city: new FormControl(null),
      country: new FormControl(null),
    });
  }

  checkUsernameAvailable(control: FormControl): Observable<{ [key: string]: boolean }> {
    const username = control.value;
    return this.http.get(`${this.baseURL}/check_username`, {params: {username}}).pipe(
      map(flag => flag ? null : {usernameTaken: true})
    );
  }

  checkEmailAvailable(currentEmail: string): AsyncValidatorFn {
    return (control: AbstractControl): Observable<{ [key: string]: boolean }> => {
      const email = control.value;
      if (currentEmail && currentEmail === email) {
        return of(null);
      }
      return this.http.get(`${this.baseURL}/check_email`, {params: {email}}).pipe(
        map(flag => flag ? null : {emailTaken: true})
      );
    };
  }

  registerUser(formData: FormData) {
    return this.http.post(`${this.baseURL}`, formData);
  }

  updateAccount(newAccount): Observable<Account> {
    return this.http.put<Account>(`${this.baseURL}`, newAccount).pipe(
      map(updatedAccount => {
        if (!updatedAccount.profilePicture) {
          updatedAccount.profilePicture = '../../assets/default-pictures/default-profile-pic.jpg';
        }
        return updatedAccount;
      })
    );
  }

  deactivateAccount(password: string): Observable<void> {
    return this.http.post<boolean>(`${this.baseURL}/authenticate`, password).pipe(
      catchError(err => throwError(err)),
      flatMap(() => this.http.delete<void>(`${this.baseURL}`))
    );
  }

  updateImage(formData: FormData) {
    return this.http.put(`${this.baseURL}/update_image`, formData);
  }
}
