package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateHTML {

    public static void createFile() {
        File file = new File("index.html");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            String html = getHTML();
            writer.write(html);
            writer.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public static String getHTML() {
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>2242 Online Temporary Frontend</title>\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js\"></script>\n" +
                "    <script src=\"https://unpkg.com/axios/dist/axios.min.js\"></script>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <!-- Hosting the images on imgur because it is easier than serving static files with Java! -->\n" +
                "\n" +
                "    <img\n" +
                "      id=\"background\"\n" +
                "      name=\"background\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/eLuiaiK.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"0\"\n" +
                "      name=\"player1-vessel\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/SAo8Jmg.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"1\"\n" +
                "      name=\"player2-vessel\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/gMAEAYo.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"2\"\n" +
                "      name=\"player3-vessel\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/9miu15J.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"3\"\n" +
                "      name=\"player4-vessel\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/CqvkK2h.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"100\"\n" +
                "      name=\"enemy1-vessel\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/tkfwJxm.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"101\"\n" +
                "      name=\"enemy2-vessel\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/ZPuTigw.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"102\"\n" +
                "      name=\"enemy3-vessel\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/imXQ5fU.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"103\"\n" +
                "      name=\"boss-vessel\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/vSl6nBS.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"1000\"\n" +
                "      name=\"player-projectile\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/dg60fBg.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"1001\"\n" +
                "      name=\"enemy-projectile\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/TZ7BZt1.png\"\n" +
                "    />\n" +
                "    <img\n" +
                "      id=\"1002\"\n" +
                "      name=\"boss-projectile\"\n" +
                "      style=\"display:none\"\n" +
                "      src=\"https://i.imgur.com/6qG7tuP.png\"\n" +
                "    />\n" +
                "\n" +
                "    <canvas id=\"gameCanvas\" width=\"640\" height=\"480\"></canvas>\n" +
                "\n" +
                "  </body>\n" +
                "  <script>\n" +
                "   let canvas = document.getElementById(\"gameCanvas\");\n" +
                "   function renderCanvas(state) {\n" +
                "       //console.log(state[0].frame)\n" +
                "       //console.log(state[1][1] + \" - \" + state[1][2])\n" +
                "       let c = document.getElementById(\"gameCanvas\");\n" +
                "       let ctx = c.getContext(\"2d\");\n" +
                "       let bg = document.getElementById(\"background\");\n" +
                "       ctx.drawImage(bg, 0, 0);\n" +
                "       for (let i = 1; i < state.length; i++) {\n" +
                "           renderSprite(state[i], ctx);\n" +
                "       }\n" +
                "   }\n" +
                "   /* Entities sent to client are stored in an array to save space.\n" +
                "    * 0 = ID, 1 = minX, 2 = minY\n" +
                "    */\n" +
                "   function renderSprite(entity, ctx) {\n" +
                "       sprite = document.getElementById(entity[0]);\n" +
                "       ctx.drawImage(sprite, entity[1], entity[2]);\n" +
                "   }\n" +
                "   /* Keys object is updated with event listeners and sent to server once a frame\n" +
                "    */\n" +
                "   let keys = {\n" +
                "       id: false,\n" +
                "       A: false,\n" +
                "       W: false,\n" +
                "       D: false,\n" +
                "       S: false,\n" +
                "       ce: false, // space\n" +
                "       Q: false\n" +
                "   };\n" +
                "   /* These two event listeners update the state of keys.\n" +
                "    */\n" +
                "   $(document).ready(function() {\n" +
                "       $(document).on(\"keydown\", event => {\n" +
                "           let key = event.code.slice(3, event.code.length);\n" +
                "           if (keys.hasOwnProperty(key)) {\n" +
                "               keys[key] = true;\n" +
                "           }\n" +
                "       });\n" +
                "       $(document).on(\"keyup\", event => {\n" +
                "           let key = event.code.slice(3, event.code.length);\n" +
                "           if (keys.hasOwnProperty(key)) {\n" +
                "               keys[key] = false;\n" +
                "           }\n" +
                "       });\n" +
                "   });\n" +
                "   let validAuthKeys = [0, 1, 2, 3];\n" +
                "   /* auth() sends an /auth GET request to the server. The server sends back an ID that\n" +
                "    * this client needs to access the client's inputbuffer. The overall authentication\n" +
                "    * is too dependent on the client code imo and I would like to fix it up if I have time.\n" +
                "    */\n" +
                "   function auth() {\n" +
                "       $(document).on(\"keyup\", event => {\n" +
                "           if (event.code.slice(3, event.code.length) == \"U\" && keys.id == false) {\n" +
                "               axios\n" +
                "                   .get(\"/auth\")\n" +
                "                   .then(res => {\n" +
                "                       if (keys.id == false && res.data.id in validAuthKeys) {\n" +
                "                           keys.id = res.data.id;\n" +
                "                           $(\"img[name='player-vessel']\").attr(\"id\", res.data.id);\n" +
                "                           loop();\n" +
                "                       } else if (res.data.id in validAuthKeys) {\n" +
                "                           keys.id = res.data.id;\n" +
                "                           $(\"img[name='player-vessel']\").attr(\"id\", res.data.id);\n" +
                "                       }\n" +
                "                   })\n" +
                "                   .catch(err => {\n" +
                "                       console.log(err);\n" +
                "                   });\n" +
                "           }\n" +
                "       });\n" +
                "   }\n" +
                "   /* Sends keys object to server on a loop.\n" +
                "    */\n" +
                "   function loop() {\n" +
                "       setTimeout(() => {\n" +
                "           axios.post(\"/cmd\", keys).then(res => {\n" +
                "               //console.log(res.data)\n" +
                "               if (res.data[0].frame) {\n" +
                "                   frameBuffer.push(res.data);\n" +
                "                   quickSort(frameBuffer, 0, frameBuffer.length - 1);\n" +
                "               } else {\n" +
                "                   let newFrame = frameBuffer[frameBuffer.length - 1];\n" +
                "                   if (newFrame) {frameBuffer.push(newFrame)};" +
                "                   console.log(res.data);\n" +
                "               }\n" +
                "               if (frameBuffer.length > 4) {\n" +
                "                   renderCanvas(frameBuffer.shift());\n" +
                "                   for (i in frameBuffer) {\n" +
                "                   }\n" +
                "               }\n" +
                "               return;\n" +
                "           });\n" +
                "           if (keys.Q) return;\n" +
                "           loop();\n" +
                "       }, 16);\n" +
                "   }\n" +
                "   auth();\n" +
                "   let frameBuffer = [];\n" +
                "   /* quickSort, partition, and swap taken from\n" +
                "    * https://khan4019.github.io/front-end-Interview-Questions/sort.html#quickSort\n" +
                "    * Added a bit of code for this to work on resposne objects rather than arrays.\n" +
                "    */\n" +
                "   function quickSort(arr, left, right) {\n" +
                "       var len = arr.length,\n" +
                "           pivot,\n" +
                "           partitionIndex;\n" +
                "       if (left < right) {\n" +
                "           pivot = right;\n" +
                "           partitionIndex = partition(arr, pivot, left, right);\n" +
                "           //sort left and right\n" +
                "           quickSort(arr, left, partitionIndex - 1);\n" +
                "           quickSort(arr, partitionIndex + 1, right);\n" +
                "       }\n" +
                "       return arr;\n" +
                "   }\n" +
                "   function partition(arr, pivot, left, right) {\n" +
                "       var pivotValue = arr[pivot][0].frame,\n" +
                "           partitionIndex = left;\n" +
                "       for (var i = left; i < right; i++) {\n" +
                "           if (arr[i][0].frame < pivotValue) {\n" +
                "               swap(arr, i, partitionIndex);\n" +
                "               partitionIndex++;\n" +
                "           }\n" +
                "       }\n" +
                "       swap(arr, right, partitionIndex);\n" +
                "       return partitionIndex;\n" +
                "   }\n" +
                "   function swap(arr, i, j) {\n" +
                "       var temp = arr[i];\n" +
                "       arr[i] = arr[j];\n" +
                "       arr[j] = temp;\n" +
                "   }\n" +
                "  </script>\n" +
                "\n" +
                "  <style>\n" +
                "    .gameCanvas {\n" +
                "      margin: 50;\n" +
                "      padding: 50;\n" +
                "    }\n" +
                "  </style>\n" +
                "</html>";
        return html;
    }
}

