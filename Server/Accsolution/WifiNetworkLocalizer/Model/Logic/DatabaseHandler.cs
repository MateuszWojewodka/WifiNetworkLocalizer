using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model.Logic
{
    internal static class DatabaseHandler
    {
        public static void AddElementToDataBase<ElementType>
           (DbContext ctx, DbSet<ElementType> dbSet, ElementType element) where ElementType : class
        {
            dbSet.Add(element);
            ctx.SaveChanges();

            PrintSuccesAddingToDatabaseMessage(element);
        }

        public static void PrintSuccesFetchingFromDatabase<T>(T fetchedElement)
        {
            Console.WriteLine($"* Fetched {typeof(T).Name} element from database.");
            PrintElementDetails(fetchedElement);
        }

        public static void PrintSuccesFetchingFromDatabase(string element)
        {
            Console.WriteLine($"* Fetched {element} from database.");
        }

        public static void PrintSuccesAddingToDatabaseMessage<T>(T elementToBeAdd)
        {
            Console.WriteLine($"* Added {typeof(T).Name} element to database.");
            PrintElementDetails(elementToBeAdd);
        }

        private static void PrintElementDetails<T>(T element)
        {
            foreach (var property in typeof(T).GetProperties())
            {
                Console.WriteLine($"    {property.Name} --> {property.GetValue(element)}");
            }
        }
    }
}
