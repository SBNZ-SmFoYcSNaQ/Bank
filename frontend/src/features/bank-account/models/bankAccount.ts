import { BankCard } from './bankCard';

interface BankAccount {
  id?: string;
  number?: string;
  balance?: number;
  bankCard?: BankCard;
}

export type { BankAccount }