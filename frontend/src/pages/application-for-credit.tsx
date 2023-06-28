import { Autocomplete, Backdrop, Box, Button, Checkbox, CircularProgress, FormControl, FormControlLabel, FormGroup, FormLabel, Modal, Radio, RadioGroup, TextField, Typography } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";

interface CreditFormValues {
  userId: number;
  amount: number;
  paymentsNumber: number;
  minRepaymentPeriod: string;
  maxRepaymentPeriod: string;
  areYouEmployed?: boolean;
  employmentEndDateOption: EmploymentEndDateOptions,
  employmentStartDate?: string;
  employmentEndDate?: string;
  salary?: number;
}

enum EmploymentEndDateOptions {
  SpecificDate = "specificDate",
  Indefinite = "indefinite",
}

const initalFormValues: CreditFormValues = {
  userId: 0,
  amount: 0,
  paymentsNumber: 0,
  minRepaymentPeriod: "",
  maxRepaymentPeriod: "",
  areYouEmployed: false,
  employmentEndDateOption: EmploymentEndDateOptions.Indefinite,
  employmentStartDate: "",
  employmentEndDate: "",
  salary: 0
}

interface User {
  id: number;
  fullName: string;
}

const style = {
  position: 'absolute' as 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

const ApplicationForCredit = () => {
  const [formValues, setFormValues] = useState<CreditFormValues>(initalFormValues);
  const [backdrop, setBackdrop] = useState(false);
  const [open, setOpen] = useState(false);
  const [users, setUsers] = useState<User[]>([]);
  const [eligibility, setEligibility] = useState("");

  const handleOpen = () => setOpen(true);

  const handleClose = () => setOpen(false);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = event.target;

    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  // const handleUserChange = () => {
  //   setFormValues((prevValues) => ({
  //     ...prevValues,
  //     userId: value.userId
  //   }));

  // }

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    setBackdrop(true);

    const { areYouEmployed, employmentEndDateOption, ...filteredFormValues } = formValues;
    console.log(filteredFormValues);

    //axios request

    setBackdrop(false);

  };

  useEffect(() => {
    fetchUsers()
  }, [])

  const fetchUsers = async () => {
    try {
      const response = await axios.get(`clients`);
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  return (
    <Box sx={{ maxWidth: 400, margin: "auto" }}>
      <Typography variant="h6" component="h2" align="center" gutterBottom>
        Application
      </Typography>
      <form onSubmit={handleSubmit}>
        <FormControl fullWidth margin="normal">
          <Autocomplete
            disablePortal
            options={users}
            getOptionLabel={(option) => option.fullName}
            renderInput={(params) => <TextField {...params} label="Choose client" required />
            }
          />
        </FormControl>
        <FormControl fullWidth margin="normal">
          <TextField
            name="amount"
            label="Amount"
            type="number"
            value={formValues.amount}
            onChange={handleChange}
            required
          />
        </FormControl>
        <FormControl fullWidth margin="normal">
          <TextField
            name="paymentsNumber"
            label="Credit Payments Number"
            type="number"
            value={formValues.paymentsNumber}
            onChange={handleChange}
            required
          />
        </FormControl>
        <FormControl fullWidth margin="normal">
          <TextField
            InputLabelProps={{ shrink: true }}
            name="minRepaymentPeriod"
            label="Minimum Repayment Period"
            type="date"
            value={formValues.minRepaymentPeriod}
            onChange={handleChange}
            required
          />
        </FormControl>
        <FormControl fullWidth margin="normal">
          <TextField
            InputLabelProps={{ shrink: true }}
            name="maxRepaymentPeriod"
            label="Maximum Repayment Period"
            type="date"
            value={formValues.maxRepaymentPeriod}
            onChange={handleChange}
            required
          />
        </FormControl>
        <FormControlLabel
          control={
            <Checkbox
              name="areYouEmployed"
              checked={formValues.areYouEmployed}
              onChange={handleChange}
            />
          }
          label="Employed?"
        />
        {formValues.areYouEmployed && (
          <>
            <FormControl fullWidth margin="normal">
              <TextField
                InputLabelProps={{ shrink: true }}
                name="employmentStartDate"
                label="Employment Start Date"
                type="date"
                value={formValues.employmentStartDate}
                onChange={handleChange}
                required
              />
            </FormControl>
            <FormControl fullWidth margin="normal">
              <FormLabel>Employment End Date</FormLabel>
              <RadioGroup
                name="employmentEndDateOption"
                value={formValues.employmentEndDateOption}
                onChange={handleChange}
                row
              >
                <FormControlLabel
                  value={EmploymentEndDateOptions.SpecificDate}
                  control={<Radio />}
                  label="Specific Date"
                />
                <FormControlLabel
                  value={EmploymentEndDateOptions.Indefinite}
                  control={<Radio />}
                  label="Indefinite"
                />
              </RadioGroup>

              {formValues.employmentEndDateOption === EmploymentEndDateOptions.SpecificDate && (
                <TextField
                  InputLabelProps={{ shrink: true }}
                  name="employmentEndDate"
                  type="date"
                  value={formValues.employmentEndDate}
                  onChange={handleChange}
                  required
                />
              )}
            </FormControl>
            <FormControl fullWidth margin="normal">
              <TextField
                name="salary"
                label="Salary"
                type="number"
                value={formValues.salary}
                onChange={handleChange}
                required
              />
            </FormControl>
          </>
        )}
        <FormGroup>
          <Button type="submit" variant="contained" sx={{ mt: 2 }}>
            Check eligibility
          </Button>
        </FormGroup>
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Typography variant="h6" component="h2">
              {eligibility}
            </Typography>
            <Box>
              <Button variant="contained"> Approve </Button>
              <Button variant="contained"> Decline </Button>
            </Box>
          </Box>
        </Modal>
        <Backdrop
          sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
          open={backdrop}
        >
          <CircularProgress color="inherit" />
        </Backdrop>
      </form>
    </Box>
  )
}

export default ApplicationForCredit