from user_interface import paretoset
import pandas as pd

def wow(skylineDist,skylineRat):
	hotels = pd.DataFrame({"price": [50, 53, 62, 87, 83, 39, 60, 44], 
	                       "distance_to_beach": [13, 21, 19, 13, 5, 22, 22, 25]})
	mask = paretoset(hotels, sense=["min", "min"])
	paretoset_hotels = hotels[mask]
	return(skylineDist)