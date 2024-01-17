# # import numpy as np

# # # Define the Q-learning algorithm
# # def q_learning(board, action, reward, next_board, q_table, learning_rate, discount_factor):
# #     # Convert the board and next_board to a string representation
# #     board_str = str(board)
# #     next_board_str = str(next_board)
    
# #     # Get the current Q-value for the (board, action) pair
# #     current_q_value = q_table.get((board_str, action), 0.0)
    
# #     # Calculate the maximum Q-value for the next state
# #     max_next_q_value = max(q_table.get((next_board_str, a), 0.0) for a in range(16))
    
# #     # Update the Q-value using the Q-learning formula
# #     new_q_value = current_q_value + learning_rate * (reward + discount_factor * max_next_q_value - current_q_value)
    
# #     # Update the Q-table with the new Q-value
# #     q_table[(board_str, action)] = new_q_value

# # # Load the board state from a .txt file
# # def load_board_state(file_path):
# #     with open(file_path, 'r') as file:
# #         board = np.loadtxt(file, dtype=int)
# #     return board

# # # Save the board state to a .txt file
# # def save_board_state(board, file_path):
# #     with open(file_path, 'w') as file:
# #         np.savetxt(file, board, fmt='%d')

# # # Example usage
# # board_file_path = '/path/to/board.txt'
# # next_board_file_path = '/path/to/next_board.txt'

# # # Load the current board state
# # board = load_board_state(board_file_path)

# # # Perform an action (e.g., place a piece)
# # action = 0  # Replace with the actual action

# # # Get the reward for the action
# # reward = 1.0  # Replace with the actual reward

# # # Load the next board state
# # next_board = load_board_state(next_board_file_path)

# # # Define the Q-table as a dictionary
# # q_table = {}

# # # Define the learning rate and discount factor
# # learning_rate = 0.1
# # discount_factor = 0.9

# # # Apply the Q-learning algorithm
# # q_learning(board, action, reward, next_board, q_table, learning_rate, discount_factor)

# # # Save the updated board state
# # save_board_state(board, board_file_path)



# import gym
# import numpy as np

# class QuartoEnv(gym.Env):
#     def __init__(self, file_path):
#         super(QuartoEnv, self).__init__()

#         self.board_size = 4
#         self.num_actions = 16
#         self.action_space = gym.spaces.Discrete(self.num_actions)
#         self.observation_space = gym.spaces.Discrete(2**self.board_size)

#         # Load states from the file
#         self.game_rounds = self.load_states(file_path)
#         self.current_round = 0

#         # Other initialization code goes here

#     def reset(self):
#         # Reset the environment to the initial state
#         self.current_round = 0
#         return self.decode_state(self.game_rounds[self.current_round][0])

#     def step(self, action):
#         # Take a step in the environment based on the given action
#         state_str, _ = self.game_rounds[self.current_round]
#         state = self.decode_state(state_str)

#         # Simulate the game to get the next state and reward
#         # For simplicity, assume a reward of 1 for a win and 0 for other states
#         next_state_str, _ = self.game_rounds[self.current_round + 1]
#         next_state = self.decode_state(next_state_str)
#         reward = 1 if self.is_winner(next_state) else 0

#         # Update the current round
#         self.current_round += 1

#         # Check if the episode is done
#         done = self.current_round >= len(self.game_rounds) - 1

#         return next_state, reward, done, {}

#     def render(self):
#         # Render the current state of the environment (optional)
#         pass

#     def close(self):
#         # Clean up resources if needed (optional)
#         pass

#     def load_states(self, file_path):
#         game_rounds = []

#         with open(file_path, 'r') as file:
#             for line in file:
#                 # Split the line into state and action
#                 state_str = line.strip()

#                 # Assume the action is not present in the file, as it's not needed for loading
#                 action_str = ""

#                 # Append the state-action pair to the list
#                 game_rounds.append((state_str, action_str))

#         return game_rounds

#     # def decode_state(self, state_str):
#     #     # Initialize an empty 4x4 board
#     #     board = np.zeros((4, 4), dtype=int)

#     #     # Split the state string into individual pieces
#     #     pieces = state_str.split()

#     #     # Iterate over pieces and update the board
#     #     for i in range(16):
#     #         row = i // 4
#     #         col = i % 4
#     #         piece = pieces[i]

#     #         if piece != '--':
#     #             if piece[-1] == '-':
#     #                 # If the last character is a dash, extract the relevant part as a number
#     #                 piece_code = piece[-4:-2]
#     #                 board[row, col] = int(piece_code)
#     #             else:
#     #                 # If the last character is not a dash, it represents a piece
#     #                 piece_code = piece[-4:]
#     #                 board[row, col] = int(piece_code)

#     #     return board

#     def decode_state(self, state_str):
#         # Initialize an empty 4x4 board
#         board = np.empty((4, 4), dtype=object)

#         # Split the state string into individual pieces
#         pieces = state_str.split()

#         # Iterate over pieces and update the board
#         for i in range(min(16, len(pieces))):
#             row = i // 4
#             col = i % 4
#             piece = pieces[i]

#             if piece != '--':
#                 if piece.isdigit():
#                     # If the piece is a number, it represents an unoccupied tile
#                     board[row, col] = int(piece)
#                 else:
#                     # If the piece is not a number, it represents a piece
#                     board[row, col] = piece

#         return board





#     def is_winner(self, state):
#         # Implement your logic to check if the player has won in the given state
#         # Replace the following line with your actual implementation
#         return False

# # Example usage of QuartoEnv with states from the text file
# env = QuartoEnv(file_path="src/main/resources/states.txt")
# observation = env.reset()

# num_episodes = 10  # Replace with the desired number of episodes

# for episode in range(num_episodes):
#     done = False
#     while not done:
#         action = env.action_space.sample()  # Replace with your Q-learning decision
#         next_observation, reward, done, _ = env.step(action)
#         # Update your Q-values and perform other learning steps




import gym
import numpy as np

class QuartoEnv(gym.Env):
    def __init__(self, file_path):
        super(QuartoEnv, self).__init__()

        self.board_size = 4
        self.num_actions = 16
        self.action_space = gym.spaces.Discrete(self.num_actions)
        self.observation_space = gym.spaces.Discrete(2**self.board_size)

        # Load states from the file
        self.game_rounds = self.load_states(file_path)
        self.current_round = 0

        # Other initialization code goes here

    def reset(self):
        # Reset the environment to the initial state
        self.current_round = 0
        return self.decode_state(self.game_rounds[self.current_round][0])

    def step(self, action):
        # Take a step in the environment based on the given action
        state_str, _ = self.game_rounds[self.current_round]
        state = self.decode_state(state_str)

        # Initialize next_state before the conditional block
        next_state = state

        # Check if there are more rounds to play
        if self.current_round < len(self.game_rounds) - 1:
            # Simulate the game to get the next state and reward
            # For simplicity, assume a reward of 1 for a win and 0 for other states
            next_state_str, _ = self.game_rounds[self.current_round + 1]
            next_state = self.decode_state(next_state_str)
            reward = 1 if self.is_winner(next_state) else 0

            # Update the current round
            self.current_round += 1

            # Check if the episode is done
            done = self.current_round >= len(self.game_rounds) - 1
        else:
            # If there are no more rounds, set reward to 0 and mark as done
            reward = 0
            done = True

        return next_state, reward, done, {}



    def render(self):
        # Render the current state of the environment (optional)
        pass

    def close(self):
        # Clean up resources if needed (optional)
        pass

    def load_states(self, file_path):
        game_rounds = []

        with open(file_path, 'r') as file:
            for line in file:
                # Split the line into state and action
                state_str = line.strip()

                # Assume the action is not present in the file, as it's not needed for loading
                action_str = ""

                # Append the state-action pair to the list
                game_rounds.append((state_str, action_str))

        return game_rounds

    def decode_state(self, state_str):
        # Initialize an empty 4x4 board
        board = np.empty((4, 4), dtype=object)

        # Split the state string into individual pieces
        pieces = state_str.split()

        # Iterate over pieces and update the board
        for i in range(min(16, len(pieces))):
            row = i // 4
            col = i % 4
            piece = pieces[i]

            if piece.isdigit():
                # If the piece is a number, it represents an unoccupied tile
                board[row, col] = int(piece)
            else:
                # If not, it represents a piece
                board[row, col] = piece

        return board

    def is_winner(self, state):
        # Implement your logic to check if the player has won in the given state
        # Replace the following line with your actual implementation
        return False

# Example usage of QuartoEnv with states from the text file
env = QuartoEnv(file_path="src/main/resources/states.txt")
observation = env.reset()

num_episodes = 10  # Replace with the desired number of episodes

for episode in range(num_episodes):
    done = False
    while not done:
        action = env.action_space.sample()  # Replace with your Q-learning decision
        next_observation, reward, done, _ = env.step(action)
        # Update your Q-values and perform other learning steps
