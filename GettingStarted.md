# Getting started with the .NET Forecasting Client #

This page details the usage of the Lokad .NET Forecasting Client, a .NET wrapper around the [Lokad Forecasting API](http://www.lokad.com/programmers-guide-forecasting-api-v3.ashx). Check the API page for in-depth explanations of the API concepts.

## Pre-requisite ##

Go to the downloads and get the Lokad Forecasting Client. This SDK comes as a single stand-alone DLL named `Lokad.Forecasting.Client.dll`. You need to add this DLL to your .NET project in Visual Studio, and add an extra DLL reference too.

## Data caveats ##

  * Dataset names, time-serie names and tags should be less than **32 characters long, with only letters and numbers**.
  * No more than 100 tags and 100 events per time-series.
  * A retry policy is already implemented within the client library.
  * Although the API has limitations over the number of time-series to be uploaded at once (and similarly the number of forecasts to be downloaded at once), you don't have to worry about those limitations as the client library takes care of splitting the requests if there is too much data for a transfer into a single call.
  * Within a single dataset, all time-series are expected to **end at the same date**, eventually inserting a final zero if needed. This is NOT an API requirement, but usually it does not really make sense to have a dataset of time-series with different ending dates. Remember that forecasts start where input time-series end.
  * Time-series starting at different times within the same dataset is fine, and a very natural situation.
  * Having a hand-few events is usually rather useless, it takes dozens of events to achieve any measurable forecast accuracy improvements.

## Sample snippet for the .NET Forecasting Client ##

The C# code snippet below illustrate how to upload and retrieve forecasts with the `Lokad.Forecasting.Client.dll`.

```
using Lokad.Forecasting.Client;

namespace MyCompany
{
   class Program
   {
      static void Main(string[] args)
      {

         var client = new ForecastingClient("ENTER_YOUR_FORECASTING_KEY_HERE");

         // Create a new dataset (no effect if it exists already)
         var container = new Dataset { 
            Name = "sales", 
            Period = "month", 
            Horizon = 6 
         };
         client.InsertDataset(container);

         // Update or Insert time-series
         var series = new TimeSerie[] { /* snipped */ };
         client.UpsertTimeSeries("sales", series, false); // merge=false

         // Wait until forecasts are ready, and then download forecasts
         var forecasts = client.GetForecasts("sales", series);
    }
}
```