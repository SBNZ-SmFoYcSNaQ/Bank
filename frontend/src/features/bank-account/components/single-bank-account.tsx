import { BankAccount } from "@features/bank-account/index";
import { Grid, Paper, Stack } from "@mui/material";
import chipImg from "../../../assets/images/chip.png";
import bankImg from "../../../assets/images/bank.png";
import fakeMasterCardImg from "../../../assets/images/fake-master-card.png";

interface Props {
  bankAccount: BankAccount;
}

function getMonthFormatted(month: number | undefined): string {
  if (month === undefined) return '';

  if (month === 0) {
    return '12';
  }

  if (month < 10) {
    return `0${month}`
  }

  return `${month}`
}

function formatCardNumber(number: string | undefined): string {
  if (!number) return '';

  return number.replace(/(.{4})/g, '$1  ')
}

const SingleBankAccount = ({ bankAccount }: Props) => {
  return (
    <Paper 
      variant="outlined"
      sx={{
        py: '28px',
        px: '24px',
        backgroundColor: '#040b2f',
        borderColor: '#333'
      }}>
      <Grid container spacing={1} sx={{ letterSpacing: '1px'}}>
        <Grid container sx={{ justifyContent: 'right', mb: '26px', alignItems: 'center' }}>
          <Grid container item xs={6} sx={{ alignItems: 'center' }}>
            <span style={{ letterSpacing: '1.5px', fontWeight: 'bold', fontSize: '1.4rem', marginRight: '3px'}}>{bankAccount.balance}</span>RSD
          </Grid>
          <Grid container item xs={6} sx={{ justifyContent: 'right', alignItems: 'center' }}>
            <img src={bankImg} alt="bank" style={{
              width: '40px',
              height: '40px',
              marginRight: '10px'
            }}/>
            Riders Bank
          </Grid>
        </Grid>
        <Grid container sx={{ justifyContent: 'left', alignItems: 'center' }}>
          <span style={{ transform: 'rotate(-90deg)', fontSize: '0.9rem', fontWeight: 'bold', color: '#aaa' }}>debit</span>
          <img src={chipImg} alt="chip" style={{
            width: '40px',
            height: '40px'
          }}/>
        </Grid>
        <Grid container sx={{ justifyContent: 'center', fontSize: '1.4rem', mb: '6px' }}>
           <span style={{ letterSpacing: '3px', fontWeight: 'bold' }}>{ formatCardNumber(bankAccount.bankCard?.number) }</span>
        </Grid>
        <Grid container sx={{ justifyContent: 'center', alignItems: 'center' }}>
          <Grid container item xs={6} sx={{ fontSize: '0.85rem'}}>
            <span style={{ color: '#aaa' }}>cvc</span> 
            <span style={{ fontWeight: 'bold', marginLeft: '4px'}}>{bankAccount.bankCard?.cvv_cvc}</span>
          </Grid>
          <Grid container item xs={5} sx={{ fontSize: '0.65rem', alignItems: 'center' }}>
            <Stack direction="column" sx={{ lineHeight: '0.6rem', mr: '8px', color: '#aaa'}}> 
              <span>VALID</span>
              <span>THRU</span>
            </Stack>
            {getMonthFormatted(bankAccount.bankCard?.expirationDate?.getMonth())} / {bankAccount.bankCard?.expirationDate?.getFullYear()}
          </Grid>
        </Grid>
        <Grid container sx={{ ml: '8px', alignItems: 'end' }}>
          <Grid container item xs={6} sx={{ alignItems: 'end'}}>
            <Stack direction="column">
              <span>{bankAccount.bankCard?.ownerName  }</span>
              <span>{bankAccount.number}</span>
            </Stack>
          </Grid>
          <Grid container item xs={6} sx={{ justifyContent: 'right'}}>
            <Stack direction="column">
              <img src={fakeMasterCardImg} alt="fake master card" style={{
                width: '80px',
                height: '80px'
              }}/>
              <span style={{ color: '#aaa' }}>mastercard</span>
            </Stack>
          </Grid>
        </Grid>
      </Grid>
    </Paper>
  )
};

export { SingleBankAccount }