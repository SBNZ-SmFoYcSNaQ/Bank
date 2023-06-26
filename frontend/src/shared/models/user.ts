interface User {
  id?: string;
  firstname?: string;
  lastname?: string;
  email?: string;
  role?: Role;
  address?: string;
  phone?: string;
  sex?: Sex;
  birthdate?: Date;
}

interface Client extends User {

}

interface BankingOfficer extends User {

}

enum Role {
  CLIENT = 0,
  BANKING_OFFICER = 1
}

enum Sex {
  MALE = 0,
  FEMALE = 1
}

export type { Client, BankingOfficer, Role, Sex }