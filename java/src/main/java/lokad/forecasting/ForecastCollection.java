package lokad.forecasting;

/**
 * @see IForecastingApi#GetForecasts(String, String, String[])
 */
public class ForecastCollection {
	public ForecastSerie[] Series;

	/**
	 * @see {@link ErrorCodes}
	 */
	public String ErrorCode;
}
