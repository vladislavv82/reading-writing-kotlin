import java.io.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*

fun main(args: Array<String>) {
        //Здесь будем использовать 2-ой способ чтения и записи
        val inputFile = File("C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\Task\\task.txt")
        val outputFile = File("C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\Task\\resultat.txt")

        // Чтение содержимого файла в строку
        val stringList = inputFile.readLines() //считываем построчно
        println("Папка прочитана")

        // Удаление пробелов из строки
        val resultString = stringList.map { it.trim().replace(Regex("\\s+"), " ") }
        println("Привожу файл к нужному результату")
        // trim убирает пробелы в начале и в конце
        //replace заменяет Regex регулярное выражение которое видет 2 и больше пробела заменяя их на 1


        // Запись результата в файл
        outputFile.writeText(resultString.joinToString("\n")) //после каждой строки \n
        println("Создаю файл result.txt")

        // Создание новой папки
        val folderResult = File("C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\folderResult")

        // Проверка на существование папки
        if (!folderResult.exists()) {
                folderResult.mkdirs() //для того что бы создать папку
                println("Создал новую папку folderResult")
        } else {
                println("Папка уже существует, перемещаю в директорию выше")
        }

        // Теперь перелаживаем файл result в нову папку folderResult
        // Можно функцию переноса вынести отдельно
        try { moveFile("C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\Task\\resultat.txt",
                        "C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\folderResult")
                println("Файл перенесен в другую папку")
        }catch (ex: IOException){
                ex.printStackTrace()
        }

        // а можно использовать прям тут
        //Files.move(outputFile.toPath(), folderResult.toPath(), StandardCopyOption.REPLACE_EXISTING)

        // Теперь будем переименовывать файл, для этого у нас есть функция, которая будет принимать старое и новое название
        try {
                renameFile("C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\folderResult\\resultat.txt",
                        "C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\folderResult\\final_resultat.txt")
        }catch (ex: IOException){
                ex.printStackTrace()
        }
        println("End")

}
//Чтение и запись 1-м способом
fun InputOutputStreamKotlin(source: String, folder: String) {

        val inputStream = FileInputStream(source)
        val outputStream = FileOutputStream(folder)
        val buffer = ByteArray(1024)

        var length = inputStream.read(buffer)
        while (length > 0) {
                outputStream.write(buffer, 0, length)
                length = inputStream.read(buffer)
        }

        inputStream.close()
        outputStream.close()
}
//Чтение и запись 3-м способом
fun BufferKotlin(source: String, folder: String) {
        val reader = BufferedReader(FileReader(source))
        val writer = BufferedWriter(FileWriter(folder))
        var line: String? = reader.readLine()

        while (line != null) {
                writer.write(line)
                writer.newLine()
                line = reader.readLine()
        }

        reader.close()
        writer.close()
}


//Функция для переноса файлов в другую папку
fun moveFile(source: String, folder: String) {
        val source = File(source)
        val folder = File(folder, source.name)

        Files.move(source.toPath(), folder.toPath(), StandardCopyOption.REPLACE_EXISTING)
}

//Функция для переименования файла
fun renameFile(old: String, new: String){
        val old = File(old)
        val new = File(new)

        if (old.exists()) {
                old.renameTo(new)
                println("Файл успешно переименован.")
        } else {
                println("Файл не существует.")
        }

}