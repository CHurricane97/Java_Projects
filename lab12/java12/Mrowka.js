var nextGeneration = function (matrix, x, y, z) {



    if (z === 2) {//N
        if (matrix[y][x] === 0) {
            matrix[y][x] = 1;
            x = x + 1;
            if (x >= matrix[0].length) {
                x = 0;
            }
            z = 3;
        } else {
            matrix[y][x] = 0;
            x = x - 1
            if (x < 0) {
                x = matrix[0].length-1;
            }
            z = 5;
        }
    } else if (z === 4) {//s
        if (matrix[y][x] === 0) {
            matrix[y][x] = 1;
            x = x - 1
            if (x < 0) {
                x = matrix[0].length-1;
            }
            z = 5;
        } else {
            matrix[y][x] = 0;
            x = x + 1
            if (x >= matrix[0].length) {
                x = 0;
            }
            z = 3;
        }
    } else if (z === 3) {//E
        if (matrix[y][x] === 0) {
            matrix[y][x] = 1;
            y = y + 1
            if (y >= matrix.length) {
                y = 0;
            }
            z = 4;
        } else {
            matrix[y][x] = 0;
            y = y - 1
            if (y < 0) {
                y = matrix.length-1;
            }
            z = 2;
        }
    } else {//W
        if (matrix[y][x] === 0) {
            matrix[y][x] = 1;
            y = y - 1
            if (y < 0) {
                y = matrix.length-1;
            }
            z = 2;
        } else {
            matrix[y][x] = 0;
            y = y + 1
            if (y >= matrix.length) {
                y = 0;
            }
            z = 4;
        }
    }

    return {mat : matrix, xx : x, yy: y, zz: z};
};