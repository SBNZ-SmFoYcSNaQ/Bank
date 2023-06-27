import { Box, Button, Checkbox, FormControl, FormControlLabel, FormGroup, FormLabel, Radio, RadioGroup, TextField, Typography } from "@mui/material";
import { useState } from "react";

interface CreditFormValues {
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

const ApplicationForCredit = () => {
  const [formValues, setFormValues] = useState<CreditFormValues>(initalFormValues);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = event.target;

    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    const { areYouEmployed, employmentEndDateOption, ...filteredFormValues } = formValues;
    console.log(filteredFormValues);
  };

  return (
    <Box sx={{ maxWidth: 400, margin: "auto" }}>
      <Typography variant="h6" component="h2" align="center" gutterBottom>
        Application
      </Typography>
      <form onSubmit={handleSubmit}>
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
          label="Are you employed?"
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
            Submit
          </Button>
        </FormGroup>
      </form>
    </Box>
  )
}

export default ApplicationForCredit