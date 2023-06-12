import './App.css';
import React, {useEffect, useState} from 'react';
import {Box, FormControl, Grid, InputLabel, MenuItem, Select} from "@mui/material";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import TableContainer from "@mui/material/TableContainer";
import Paper from "@mui/material/Paper";
import TableHead from "@mui/material/TableHead";
import Table from "@mui/material/Table";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import Chart from "./components/Chart";
import Button from '@mui/material/Button';
import axios from "axios";
import {tab} from "@testing-library/user-event/dist/tab";

function App() {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [currencySelectValue, setCurrencySelectValue] = React.useState('');
  const [currentDb, setCurrentDb] = React.useState('');
  const [currencies, setCurrencies] = React.useState([]);
  const [chartData, setChartData] = React.useState([]);
  const [queryTime, setQueryTime] = React.useState('0');

  const [tableData, setTableData] = React.useState({
    testCase1: {elastic: 0, mongoDB: 0, postgres: 0},
    testCase2: {elastic: 0, mongoDB: 0, postgres: 0},
    testCase3: {elastic: 0, mongoDB: 0, postgres: 0},
    testCase4: {elastic: 0, mongoDB: 0, postgres: 0},
  });



  useEffect(() => {
    axios.get('http://localhost:8080/currencies').then(resp => {
      setCurrencies(resp.data);
    });
  }, []);



  return (
      <div className="App" style={{display:"flex",alignItems:"center", flexDirection:"column"}}>

        <Grid container spacing={2} sx={{maxWidth: '1300px',mt:'50px',alignItems: 'flex-end',justifyContent: 'center' }}>

          <Grid item xs={2}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DemoContainer components={['DatePicker']}>
                <DatePicker label="From" value={startDate} onChange={(newValue) => setStartDate(newValue)} />
              </DemoContainer>
            </LocalizationProvider>
          </Grid>

          <Grid item xs={2}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DemoContainer components={['DatePicker']}>
                <DatePicker label="To" value={endDate} onChange={(newValue) => setEndDate(newValue)} />
              </DemoContainer>
            </LocalizationProvider>
          </Grid>

          <Grid item xs={2}>
            <Box >
              <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">currency</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={currencySelectValue}
                    label="currency"
                    onChange={(event)=>setCurrencySelectValue(event.target.value)}
                >
                  {currencies.map((currency)=><MenuItem value={currency}>{currency}</MenuItem>)}

                </Select>
              </FormControl>
            </Box>
          </Grid>

          <Grid item xs={2}>
            <Box >
              <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Database</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={currentDb}
                    label="currency"
                    onChange={(event)=>setCurrentDb(event.target.value)}
                >
                  <MenuItem value={'PostgreSQL'}>PostgreSQL</MenuItem>
                  <MenuItem value={'MongoDB'}>MongoDB</MenuItem>
                  <MenuItem value={'Elastic'}>Elastic</MenuItem>
                </Select>
              </FormControl>
            </Box>
          </Grid>

          <Grid item xs={1}><Button variant="outlined" sx={{mb:'10px'}} onClick={()=>{
            axios.get('http://localhost:8080/getChartData',{params:{
              startDate:startDate.format('YYYY-MM-DD'),
              endDate:endDate.format('YYYY-MM-DD'),
                currency:currencySelectValue,
                database:currentDb
              }}).then((response) => {
              console.log(response.data)
              const data = response.data.currencyModel.map((curr)=>{return {date:curr.date, high:curr.high, low: curr.low}})
              setQueryTime(response.data.time)
              setChartData(data)
            });
          }
          }>Display</Button></Grid>

          <Grid item xs={1}>
            <h3 style={{color:'darkgray'}}>{`${queryTime}ms`}</h3>
          </Grid>

        </Grid>

        <Chart chartData={chartData}/>


        <TableContainer component={Paper} sx={{maxWidth: '80%'}}>
          <Table sx={{ backgroundColor:'#F3F4F7' }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell align="center">Test case</TableCell>
                <TableCell align="center">PostgreSQL</TableCell>
                <TableCell align="center">MongoDB</TableCell>
                <TableCell align="center">Elastic</TableCell>
                <TableCell align="center"></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>

                  <TableRow
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell align="center">Maksymalna wartość waluty w 2023 EUR/USD</TableCell>
                    <TableCell align="center">{`${tableData.testCase1.postgres}ms`}</TableCell>
                    <TableCell align="center">{`${tableData.testCase1.mongoDB}ms`}</TableCell>
                    <TableCell align="center">{`${tableData.testCase1.elastic}ms`}</TableCell>
                    <TableCell align="center"><Button variant="outlined" onClick={()=>{
                      axios.get('http://localhost:8080/testCase1').then((response) => {
                        setTableData({...tableData,testCase1: {elastic:response.data.Elastic, postgres:response.data.Postgres, mongoDB:response.data.MongoDB }})
                    })}}>Run</Button></TableCell>
                  </TableRow>

              <TableRow
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              >
              <TableCell align="center">Dni kiedy wartość podczas otwarcia była między 70% a 100% wartości maksymalnej</TableCell>
              <TableCell align="center">{`${tableData.testCase2.postgres}ms`}</TableCell>
              <TableCell align="center">{`${tableData.testCase2.mongoDB}ms`}</TableCell>
              <TableCell align="center">{`${tableData.testCase2.elastic}ms`}</TableCell>
              <TableCell align="center"><Button variant="outlined" onClick={()=>{
                axios.get('http://localhost:8080/testCase2').then((response) => {
                  setTableData({...tableData,testCase2: {elastic:response.data.Elastic, postgres:response.data.Postgres, mongoDB:response.data.MongoDB }})
                })}}>Run</Button></TableCell>
            </TableRow>

              <TableRow
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              >
                <TableCell align="center">Znajdź dni w których wartość otwarcia jest większa niż zamknięcia</TableCell>
                <TableCell align="center">{`${tableData.testCase3.postgres}ms`}</TableCell>
                <TableCell align="center">{`${tableData.testCase3.mongoDB}ms`}</TableCell>
                <TableCell align="center">{`${tableData.testCase3.elastic}ms`}</TableCell>
                <TableCell align="center"><Button variant="outlined" onClick={()=>{
                  axios.get('http://localhost:8080/testCase3').then((response) => {
                    setTableData({...tableData,testCase3: {elastic:response.data.Elastic, postgres:response.data.Postgres, mongoDB:response.data.MongoDB }})
                  })}}>Run</Button></TableCell>
              </TableRow>

              <TableRow
                  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              >
                <TableCell align="center">Posortuj pogrupowane dane (rok, miesiąc) dla waluty</TableCell>
                <TableCell align="center">{`${tableData.testCase4.postgres}ms`}</TableCell>
                <TableCell align="center">{`${tableData.testCase4.mongoDB}ms`}</TableCell>
                <TableCell align="center">{`${tableData.testCase4.elastic}ms`}</TableCell>
                <TableCell align="center"><Button variant="outlined" onClick={()=>{
                  axios.get('http://localhost:8080/testCase4').then((response) => {
                    setTableData({...tableData,testCase4: {elastic:response.data.Elastic, postgres:response.data.Postgres, mongoDB:response.data.MongoDB }})
                  })}}>Run</Button></TableCell>
              </TableRow>

            </TableBody>
          </Table>
        </TableContainer>

      </div>
  );
}

export default App;
