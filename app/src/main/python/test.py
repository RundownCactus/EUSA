from user_interface import paretoset
import pandas as pd
import re
def wow(skylineDist,skylineRat):
	distance = skylineDist.split(',')
	rating = skylineRat.split(',')
	dist = [float(re.sub('[^0123456789.]+' ,'', i)) for i in distance]
	rat = [float(re.sub('[^0123456789.]+' ,'', i)) for i in rating]
	hotels = pd.DataFrame({"distance": dist,
	                       "rating": rat})
	mask = paretoset(hotels, sense=["min", "max"])
	paretoset_hotels = hotels[mask]
	return(paretoset_hotels.index.tolist()[0])