import { BankAccount } from "@features/bank-account/index";
import { Button, Grid, Paper, Stack } from "@mui/material";
import chipImg from "../../../assets/images/chip.png";
import bankImg from "../../../assets/images/bank.png";
import fakeMasterCardImg from "../../../assets/images/fake-master-card.png";
import { SuspiciousTransaction } from "..";
import axios, { AxiosError } from "axios";
import { ToastContainer, toast } from "react-toastify";
import { optionalIndexedAccessType } from "@babel/types";

interface Props {
    transaction: SuspiciousTransaction;
    onAccept: Function;
    onCancel: Function;
}

function formatCardNumber(number: string | undefined): string {
    if (!number) return '';

    return number.replace(/(.{4})/g, '$1  ')
}

const handleAccept = async (id: string | undefined) => {
    try {
        const response = await axios.get(`transaction/suspicious/accept/` + id);
        toast.success("Transaction accepted successfully!", { position: toast.POSITION.BOTTOM_CENTER });    
    }
        catch (err: any) {
        toast.error(err.response?.data.error, {
            position: toast.POSITION.BOTTOM_CENTER,
        });
    }
};

const handleCancel = async (id: string | undefined) => {
    try {
        const response = await axios.get(`transaction/suspicious/cancel/` + id);
        toast.success("Transaction canceled successfully!", { position: toast.POSITION.BOTTOM_CENTER });
    } catch (err: any) {
        toast.error(err.response?.data.error, {
            position: toast.POSITION.BOTTOM_CENTER,
        });
    }
}

const SingleSuspiciousTransaction = ({ transaction, onAccept, onCancel }: Props) => {
    return (
        <Paper
            variant="outlined"
            sx={{
                py: '28px',
                px: '24px',
                backgroundColor: '#040b2f',
                borderColor: '#333'
            }}>
            <ToastContainer />
            <Grid container spacing={1} sx={{ letterSpacing: '1px' }}>
                <Grid container sx={{ justifyContent: 'right', mb: '26px', alignItems: 'center' }}>
                    <Grid container item xs={6} sx={{ alignItems: 'center' }}>
                        <span style={{ letterSpacing: '1.5px', fontWeight: 'bold', fontSize: '1.4rem', marginRight: '3px' }}>{transaction.amount}</span>RSD
                    </Grid>
                    <Grid container item xs={6} sx={{ justifyContent: 'right', alignItems: 'center' }}>
                        <img src={bankImg} alt="bank" style={{
                            width: '40px',
                            height: '40px',
                            marginRight: '10px'
                        }} />
                    </Grid>
                </Grid>
                {/* <Grid container sx={{ justifyContent: 'left', alignItems: 'center' }}>
          <span style={{ transform: 'rotate(-90deg)', fontSize: '0.9rem', fontWeight: 'bold', color: '#aaa' }}>debit</span>
          <img src={chipImg} alt="chip" style={{
            width: '40px',
            height: '40px'
          }}/>
        </Grid> */}
                <Grid container sx={{ justifyContent: 'center', fontSize: '1.4rem', mb: '6px' }}>
                    <span style={{ letterSpacing: '3px', fontWeight: 'bold' }}>{formatCardNumber(transaction.bankAccountNumber)}</span>
                </Grid>
                <Grid container sx={{ ml: '8px', alignItems: 'end' }}>
                    <Grid container item xs={6} sx={{ alignItems: 'end' }}>
                        <Stack direction="column">
                            <span>{transaction.location}</span>
                        </Stack>
                    </Grid>
                    <Grid container item xs={6} sx={{ justifyContent: 'right' }}>
                        <Stack direction="column">
                            <span style={{ color: '#aaa' }}>{transaction.message}</span>
                        </Stack>
                    </Grid>
                    <Grid container item xs={12} sx={{ justifyContent: 'right', mt: '10px' }}>
                        <Button variant="contained" style={{ marginRight: '10px' }} onClick={(e) => onAccept(transaction.id)}>Accept</Button>
                        <Button variant="outlined" onClick={(e) => onCancel(transaction.id)}>Cancel</Button>
                    </Grid>
                </Grid>
            </Grid>
        </Paper>
    )
};

export { SingleSuspiciousTransaction }