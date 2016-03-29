# Getting started with Java Forecasting Client #

This page details the usage of the Lokad Java Forecasting Client, a Java wrapper around the [Lokad Forecasting API](http://www.lokad.com/programmers-guide-forecasting-api-v3.ashx). Check the API page for in-depth explanations about the API concepts.

## Pre-requisite ##

Go to the download section and get the Java version of Lokad Forecasting Client. This SDK comes as zip archive containing:

  * lokad-forecasting-client.jar
  * xstream-1.3.1.jar

You need to reference these both JARs in your Java project.

## Data caveats ##

  * Dataset names, time-serie names and tags should be less than **32 characters long, with only letters and numbers**.
  * No more than 100 tags and 100 events per time-series.
  * A retry policy is already implemented within the client library.
  * Although the API has limitations over the number of time-series to be uploaded at once (and similarly the number of forecasts to be downloaded at once), you don't have to worry about those limitations as the client library takes care of splitting the requests if there is too much data for a transfer into a single call.
  * Within a single dataset, all time-series are expected to **end at the same date**, eventually inserting a final zero if needed. This is NOT an API requirement, but usually it does not really make sense to have a dataset of time-series with different ending dates. Remember that forecasts start where input time-series end.
  * Time-series starting at different times within the same dataset is fine, and a very natural situation.
  * Having a hand-few events is usually rather useless, it takes dozens of events to achieve any measurable forecast accuracy improvements.

## Sample snippet for the Java Forecasting Client ##

The C# code snippet below illustrate how to upload and retrieve forecasts.
String apikey = "ENTER_YOUR_FORECASTING_KEY_HERE";
String endpoint = "http://api.lokad.com/rest/forecasting3";
//String endpoint = "http://sandbox-api.lokad.com/rest/forecasting3"; // sandbox

ForecastingClient client = new ForecastingClient(apikey, endpoint);

// Create a new dataset (no effect if it exists already)
Dataset container = new Dataset();
container.Name = "mydata";
container.Period = "week";
container.Horizon = 4;
client.InsertDataset(container); 

// Update or Insert time-series
TimeSerie[] series = new TimeSerie[] { /* snipped */ };
client.UpsertTimeSeries(container.Name, series, false); // merge=false

String[] serieNames = new String[] { /* snipped */ };
// Wait until forecasts are ready, and then download forecasts
ForecastSerie[] forecasts = client.GetForecasts(container.Name, serieNames); }}}}```