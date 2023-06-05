import React from 'react';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);

export const options = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Chart.js Line Chart',
        },
        customCanvasBackgroundColor: {
            color: 'red',
        }
    },
};



const Chart= ({chartData}) => {
    const labels = chartData.map(obj => obj.date);

    const data = {
        labels,
        datasets: [
            {
                label: 'low',
                data: chartData.map(obj => obj.low),
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
            },
            {
                label: 'high',
                data: chartData.map(obj => obj.high),
                borderColor: 'rgb(53, 162, 235)',
                backgroundColor: 'rgba(53, 162, 235, 0.5)',
            },
        ],
    };
    return (<div style={{width:'800px',height:'500px',marginTop:'20px'}}><Line options={options} data={data} /></div>);
}

export default Chart