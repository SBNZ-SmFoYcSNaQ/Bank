import { Box, Button, CircularProgress, Container, Grid, Typography } from "@mui/material";
import { BankAccount, SingleBankAccount } from "../features/bank-account";
import { useFetch, usePost } from "../shared/index";
import { toast } from "react-toastify";


const BankAccountPages = () => {
  const { error, loading, data: bankAccountList, setData: setBankAccountList} = useFetch<BankAccount[]>('users/me/bankaccounts');
  const {
     error: createAccountError, 
     pending: creationPending,
     post: createBankAccount } = usePost<any, BankAccount>('users/me/bankaccounts/add', {}, setBankAccountList);

  if (loading) return ( 
    <Container sx={{ justifyContent: 'center', mt: '50px'}}>
      <CircularProgress color="inherit" />
    </Container>
  )

  if (error) return (
    <Container sx={{ justifyContent: 'center', mt: '50px'}}>
      <div>{error}</div>
    </Container>
  )
  
  if (createAccountError) {
    toast.error("Error creating bank account.");
  }

  const addBankAccount = async () => {
    const newBankAccount = await createBankAccount();
    if (!creationPending && !createAccountError && bankAccountList && newBankAccount) {
        toast.success("Bank account created successfully.");
        setBankAccountList([...bankAccountList, newBankAccount]);
    }
  }

  return (
    <Container sx={{ py: '12px' }}>
      <Typography 
        variant="h3"
        sx={{ textAlign: 'center', mb: '24px' }}>
          Bank Accounts 
      </Typography>
      <Button onClick={addBankAccount} variant="contained" sx={{ mb: '50px' }} disabled={creationPending}>{ creationPending ? "Creating..." : "Create New Bank Account" }</Button>
      { bankAccountList && bankAccountList.length === 0 && <Box>
        You don't have any accounts yet
      </Box>}
      {bankAccountList && bankAccountList.length !== 0 && <Grid container spacing={3}>
        { bankAccountList.map(x => (
          <Grid item xs={12} sm={9} md={8} lg={6} key={x.id}>
            <SingleBankAccount bankAccount={x}/>
          </Grid>
        ))}
      </Grid> }
    </Container>
  )
}

export default BankAccountPages;