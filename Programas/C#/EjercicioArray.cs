string[] lista = ["B123", "C234", "A345", "C15", "B177", "G3003", "C235", "B179"];

foreach (string str in lista)
{
    if (str.StartsWith("B"))
    {
        Console.WriteLine(str);
    }
}