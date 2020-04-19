package com.java.mydevops.mahout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class Recommend {

    public static List<String> recommend(long userId) throws IOException, TasteException {
		File userPreferencesFile = new File("data");
		// Creating data model
		DataModel dataModel = new FileDataModel(userPreferencesFile);

		// Creating UserSimilarity object.
		UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
		// Creating UserNeighbourHHood object.

		UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(3, userSimilarity, dataModel);
		// Create UserRecomender
		Recommender genericRecommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);

		// Recommend 3 music for user
		//LongPrimitiveIterator iterator = dataModel.getUserIDs();
		List<RecommendedItem> recommendedItems = genericRecommender.recommend(userId, 3);
		List<String> result=new  ArrayList<String>();
        if (!recommendedItems.isEmpty()) {
			for (RecommendedItem recommendedItem : recommendedItems) {
			    // Return Recommended Item Id and  The strength of the preference for the recommended item
				result.add(recommendedItem.getItemID()+"-"+recommendedItem.getValue());
			}
		}

		return result;
	}

}
