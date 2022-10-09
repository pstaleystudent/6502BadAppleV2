# 6502BadAppleV2
Bad Apple played on 6502 simulator, redux

# Encoder
Given a folder of 32x32 black and white PNG images with a name starting with 'out', encodes all images into a continuous 7-bit run-length format for playback by the ASM. Output is placed in out.txt, and should be moved to the ASM file after DCB.

# ASM
Assembles and executes on http://6502.cdot.systems/
Plays at 5fps on max simulator speed. Note: simulator has limited memory capacity and cannot play larger videos that Bad Apple.

# Bad Apple
Currently the DCB instruction has Bad Apple encoded. Watch it here: https://www.youtube.com/watch?v=qsx5hKWRqpY. The repeated solid black frames from the start and end were removed to fit on the 6502.
