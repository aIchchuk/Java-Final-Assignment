/*
Question1 b)

Imagine you have a secret decoder ring with rotating discs labeled with the lowercase alphabet. You're given a
message s written in lowercase letters and a set of instructions shifts encoded as tuples (start_disc, end_disc,
direction). Each instruction represents rotating the discs between positions start_disc and end_disc (inclusive
either clockwise (direction = 1) or counter-clockwise (direction = 0). Rotating a disc shifts the message by one
letter for each position moved on the alphabet (wrapping around from ‘z’ to ‘a’ and vice versa).
*/


public class DecoderRing {

    public static String decodeMessage(String message, int[][] instructions) {
        // Convert message to a char array for easy manipulation
        char[] chars = message.toCharArray();
        
        // Apply each instruction
        for (int[] instruction : instructions) {
            int startDisc = instruction[0];
            int endDisc = instruction[1];
            int direction = instruction[2];
            
            // Apply rotation for each disc in the range
            for (int i = startDisc; i <= endDisc; i++) {
                chars[i] = rotateChar(chars[i], direction);
            }
        }
        
        // Convert the char array back to a string
        return new String(chars);
    }

    // Helper method to rotate a character
    private static char rotateChar(char c, int direction) {
        if (direction == 1) {
            // Rotate clockwise
            return (char) ((c - 'a' + 1) % 26 + 'a');
        } else {
            // Rotate counter-clockwise
            return (char) ((c - 'a' - 1 + 26) % 26 + 'a');
        }
    }

    public static void main(String[] args) {
        // Example usage
        String message = "hello";
        int[][] instructions = {
            {0, 2, 1}, // Rotate discs 0 to 2 clockwise
            {1, 4, 0}  // Rotate discs 1 to 4 counter-clockwise
        };

        String decodedMessage = decodeMessage(message, instructions);
        System.out.println("Decoded Message: " + decodedMessage);
    }
}
