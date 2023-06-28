import { Box, Button, CircularProgress, Container, Grid, Typography } from "@mui/material";
import { useCallback, useEffect, useState } from "react";
import { BankAccount, SingleBankAccount } from "../features/bank-account";
import { useFetch } from "../shared/index";
import React from "react";
import { SuspiciousTransaction, SingleSuspiciousTransaction } from "../features/suspicious-transactions";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";

const SuspictiousTransactionsPage = () => {
    const [transactions, setTransactions] = React.useState<SuspiciousTransaction[]>([]);
    const [loading, setLoading] = React.useState(true);

    const fetchTransactions = useCallback(async () => {
      try{
        const response = await axios.get(`transaction/suspicious`);
        const data = response.data;
        console.log(data)
        setTransactions(data);
        setLoading(false)
      }catch(err: any){
          console.log(err)
      }
      }, []);

      React.useEffect(() => {
        fetchTransactions()
      }, [fetchTransactions]);

      const handleAccept = async (id: string | undefined) => {
        try {
          const response = await axios.get(`transaction/suspicious/accept/` + id);
          toast.success("Transaction accepted successfully!", { position: toast.POSITION.BOTTOM_CENTER });
          fetchTransactions(); // Call fetchTransactions after successfully accepting the transaction
        } catch (err: any) {
          toast.error(err.response?.data.error, {
            position: toast.POSITION.BOTTOM_CENTER,
          });
        }
      };
    
      const handleCancel = async (id: string | undefined) => {
        try {
          const response = await axios.get(`transaction/suspicious/cancel/` + id);
          toast.success("Transaction canceled successfully!", { position: toast.POSITION.BOTTOM_CENTER });
          fetchTransactions(); // Call fetchTransactions after successfully canceling the transaction
        } catch (err: any) {
          toast.error(err.response?.data.error, {
            position: toast.POSITION.BOTTOM_CENTER,
          });
        }
      };

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
      <ToastContainer />
      <Typography 
        variant="h3"
        sx={{ textAlign: 'center', mb: '24px' }}>
          Suspicious Transactions
      </Typography>
      { transactions && transactions.length === 0 && loading === false && <Box>
        You don't have any suspicious transactions
      </Box>}
      {transactions && transactions.length !== 0 && loading === false && <Grid container spacing={3}>
        { transactions.map(x => (
          <Grid item xs={12} sm={9} md={8} lg={6} key={x.id}>
            <SingleSuspiciousTransaction transaction={x} onAccept={handleAccept} onCancel={handleCancel}/>
          </Grid>
        ))}
      </Grid> }
    </Container>
  )
}

export default SuspictiousTransactionsPage;