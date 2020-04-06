import {Component, OnInit} from '@angular/core';
import {Account} from '../../model/account';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {EditAccountComponent} from '../../modal/edit-account/edit-account.component';
import {AuthenticationService} from '../../service/authentication.service';
import {DeactivateAccountComponent} from '../../modal/deactivate-account/deactivate-account.component';
import {AccountService} from '../../service/account.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-account-details-card',
  templateUrl: './account-details-card.component.html',
  styleUrls: ['./account-details-card.component.css']
})
export class AccountDetailsCardComponent implements OnInit {

  account: Account;

  constructor(private modalService: NgbModal,
              private authenticationService: AuthenticationService,
              private accountService: AccountService,
              private router: Router) {
  }

  ngOnInit() {
    this.account = this.authenticationService.user.value.account;
  }

  onEdit() {
    const modalRef = this.modalService.open(EditAccountComponent, {centered: true, backdrop: 'static'});
    modalRef.componentInstance.account = this.account;
    modalRef.result.then(newAccount => this.updateLocalStorage(newAccount), this.onError);
  }

  onDeactivate() {
    const modalRef = this.modalService.open(DeactivateAccountComponent, {centered: true, backdrop: 'static'});
    modalRef.result.then(() => this.authenticationService.logout());
  }

  onImageSelected(event) {
    const image = event.target.files[0] as File;
    if (image) {
      const formData = new FormData();
      formData.append('image', image);
      this.accountService.updateImage(formData)
        .subscribe(newAccount => this.updateLocalStorage(newAccount));
    }
  }

  updateLocalStorage(newAccount) {
    const userData = JSON.parse(localStorage.getItem('userData'));
    userData.account = newAccount;
    localStorage.setItem('userData', JSON.stringify(userData));
    this.authenticationService.autoLogin();
    this.account = newAccount;
  }

  onError = err => this.router.navigate(['/error'], {queryParams: {status: err.status}, replaceUrl: true});
}
