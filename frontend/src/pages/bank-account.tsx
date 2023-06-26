import { Box, Button, CircularProgress, Container, Grid, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { BankAccount, SingleBankAccount } from "../features/bank-account";
import { useFetch } from "../shared/index";

const bankAccountsMocked: BankAccount[] = [
  {
    id: "d1fe06b6-563d-4602-a9c0-55138cb4c5c3",
    number: "936175028471356920",
    balance: 120000,
    bankCard: {
      id: "51740356-1602-4ee1-a0da-5b93b72bb680",
      number: "5848569512342342",
      expirationDate: new Date(2023, 12, 12),
      ownerName: "Sladjan Stoka",
      cvv_cvc: "676"
    }
  },
  {
    id: "98284a1e-136a-4a4f-8e33-cdd6be792352",
    number: "257839614062738495",
    balance: 25000,
    bankCard: {
      id: "c15d4f06-3c61-4f07-914d-0d2d00f6b120",
      number: "1357924680146789",
      expirationDate: new Date(2023, 10, 20),
      ownerName: "Alice Johnson",
      cvv_cvc: "789"
    }
  },
  {
    id: "8f632c31-dcbc-478b-865e-89ff50a01713",
    number: "814692053284167592",
    balance: 75000,
    bankCard: {
      id: "37be382b-0c24-4f6e-8a2a-9d3567550edf",
      number: "1234567890234565",
      expirationDate: new Date(2025, 3, 15),
      ownerName: "Jane Doe",
      cvv_cvc: "456"
    }
  },
  {
    id: "3c1df2b0-8742-4c63-934a-9e62a3ef84e5",
    number: "598432176509273815",
    balance: 50000,
    bankCard: {
      id: "6a5b43c8-ec7d-4ef1-aa45-89d4f6e48f52",
      number: "9876543210123432",
      expirationDate: new Date(2024, 6, 30),
      ownerName: "John Smith",
      cvv_cvc: "123"
    }
  }
]

const BankAccountPages = () => {
  const [bankAccounts, setBankAccounts] = useState<BankAccount[] | null>(bankAccountsMocked);
  const { error, loading, data: bankAccountList} = useFetch<BankAccount[]>(`${process.env.REACT_APP_API_URL}/bankAccounts`);

  // if (loading) return ( 
  //   <Container sx={{ justifyContent: 'center', mt: '50px'}}>
  //     <CircularProgress color="inherit" />
  //   </Container>
  // )

  // if (error) return (
  //   <Container sx={{ justifyContent: 'center', mt: '50px'}}>
  //     <div>{error}</div>
  //   </Container>
  // )

  return (
    <Container sx={{ py: '12px' }}>
      <Typography 
        variant="h3"
        sx={{ textAlign: 'center', mb: '24px' }}>
          Bank Accounts 
      </Typography>
      <Button variant="contained" sx={{ mb: '50px' }}>Create New Bank Account</Button>
      { bankAccounts && bankAccounts.length === 0 && <Box>
        You don't have any accounts yet
      </Box>}
      {bankAccounts && bankAccounts.length !== 0 && <Grid container spacing={3}>
        { bankAccounts.map(x => (
          <Grid item xs={12} sm={9} md={8} lg={6} key={x.id}>
            <SingleBankAccount bankAccount={x}/>
          </Grid>
        ))}
      </Grid> }
    </Container>
  )
}

export default BankAccountPages;