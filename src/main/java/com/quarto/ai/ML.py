import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error

def count_features(properties, feature):
    for i in range(len(properties)):
        zeros = properties[i].count(0)
        ones = properties[i].count(1)
        empties = properties[i].count(-1)

        if max(zeros, ones) == 1 and empties == 3:
            feature[0] += 1
        elif max(zeros, ones) == 2 and empties == 2:
            feature[1] += 1
        elif max(zeros, ones) == 3 and empties == 1:
            feature[2] += 1
        elif max(zeros, ones) == 4 and empties == 0:
            feature[3] += 1

def extract_features(boardstate):
    #1s,2s,3s,4s
    features=[0,0,0,0]

    #rows
    rows_properties = []
    for i in range(0, 16, 4):
        rows_properties.append([boardstate[i], boardstate[i+4], boardstate[i+8], boardstate[i+12]])
        rows_properties.append([boardstate[i+1], boardstate[i+1+4], boardstate[i+1+8], boardstate[i+1+12]])
        rows_properties.append([boardstate[i+2], boardstate[i+2+4], boardstate[i+2+8], boardstate[i+2+12]])
        rows_properties.append([boardstate[i+3], boardstate[i+3+4], boardstate[i+3+8], boardstate[i+3+12]])

    count_features(rows_properties,features)
    
    #cols
    cols_properties = []
    for i in range(4):
        cols_properties.append([boardstate[i], boardstate[i+16], boardstate[i+32], boardstate[i+48]])
        cols_properties.append([boardstate[i+1], boardstate[i+1+16], boardstate[i+1+32], boardstate[i+1+48]])
        cols_properties.append([boardstate[i+2], boardstate[i+2+16], boardstate[i+2+32], boardstate[i+2+48]])
        cols_properties.append([boardstate[i+3], boardstate[i+3+16], boardstate[i+3+32], boardstate[i+3+48]])

    count_features(cols_properties,features)
    
    #diagnal1
    diag1_properties=[]
    diag1_properties.append([boardstate[0], boardstate[20], boardstate[40], boardstate[60]])
    diag1_properties.append([boardstate[0+1], boardstate[20+1], boardstate[40+1], boardstate[60+1]])
    diag1_properties.append([boardstate[0+2], boardstate[20+2], boardstate[40+2], boardstate[60+2]])
    diag1_properties.append([boardstate[0+3], boardstate[20+3], boardstate[40+3], boardstate[60+3]])

    count_features(diag1_properties,features)

    #diagnal2
    diag2_properties=[]
    diag2_properties.append([boardstate[12], boardstate[28], boardstate[36], boardstate[48]])
    diag2_properties.append([boardstate[12+1], boardstate[28+1], boardstate[36+1], boardstate[48+1]])
    diag2_properties.append([boardstate[12+2], boardstate[28+2], boardstate[36+2], boardstate[48+2]])
    diag2_properties.append([boardstate[12+3], boardstate[28+3], boardstate[36+3], boardstate[48+3]])

    return features


# Load the data
data = pd.read_csv('src/main/resources/gamedata.csv')

# Split the data into features (X) and target (y)
X = data.iloc[:, :-1]  # All columns except the last
y = data.iloc[:, -1]  # Only the last column

num_rows, num_cols = X.shape
features_df = pd.DataFrame(index=range(num_rows), columns=['1s', '2s', '3s', '4s'])

for i in range(num_rows):
# Get the first row using iloc (integer-location based indexing)
    row_iloc = X.iloc[i]
    features_list = extract_features(row_iloc)
    features_df.loc[i] = features_list

print(features_df)

# Split the data into training and test sets
X_train, X_test, y_train, y_test = train_test_split(features_df, y, test_size=0.34, random_state=42)

# Create and train the model
model = LinearRegression()
model.fit(X_train, y_train)

# Map weights to feature names
feature_names = features_df.columns
weights = model.coef_

# Print overall weights
print("Overall Weights:")
for feature, weight in zip(feature_names, weights):
    print(f"{feature}: {weight}")
