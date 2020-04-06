import { Account } from './account';

export class User {

  public account: Account;
  readonly token: string;
  readonly tokenExpirationDate: Date;

  constructor(account: Account, token: string, tokenExpirationDate: Date) {
    this.account = account;
    this.token = token;
    this.tokenExpirationDate = tokenExpirationDate;
  }

  get getToken() {
    if (!this.tokenExpirationDate || new Date() > this.tokenExpirationDate) {
      return null;
    }
    return this.token;
  }
}
