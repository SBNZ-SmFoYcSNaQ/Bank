interface SuspiciousTransaction {
    id?: string;
    amount?: number;
    creationTime?: string;
    location?: string;
    bankAccountNumber?: string;
    message?: string;
  }

  export type { SuspiciousTransaction }