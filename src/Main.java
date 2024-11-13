import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.print("Inserisci il seed URL: ");
            String seed = scanner.next();

            if (seed.startsWith("http"))
            {
                crawl(seed, 0);
                return;
            }
            else
                System.out.println("⚠️ Errore: inserire un link assoluto!");
        } while (true);
    }

    public static void crawl(String seed, int depth)
    {
        if (depth > 3) return; //caso base

        try
        {
            Document doc = Jsoup.connect(seed).get();
            Elements links = doc.select("a[href]");
            for (Element link : links)
            {
                String newURL = link.absUrl("href");
                if (newURL.startsWith("http"))
                {
                    System.out.println("\tLink: " + newURL);
                    crawl(newURL, depth + 1);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("⚠️ IOException: errore nel caricamento (" + seed + ")");
        }
    }
}