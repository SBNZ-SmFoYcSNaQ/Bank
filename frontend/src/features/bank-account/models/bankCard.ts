interface BankCard {
  id?: string;
  number?: string;
  expirationDate?: Date;
  ownerName?: string;
  cvv_cvc?: string;
}

export type { BankCard }