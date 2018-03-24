using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Database_Entities;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Handlers
{
    public class Localization : ILocalization
    {
        public Point GetXYLocalizationPoint(ThreeMacIds threeMacIds)
        {
            throw new NotImplementedException();
        }

        public List<string> GetPossibleBuildings()
        {
            throw new NotImplementedException();
        }

        public ThreeMacIds GetThreeMeasurmentMacIds(string buildingName)
        {
            throw new NotImplementedException();
        }

        public void SetThreeMeasurmentPoints(ThreeMacIds threeMacIds)
        {
            using (var ctx = new WifiLocalizerContext())
            {
                TryAddElementToDataBase(ctx, ctx.ThreeMeasurmentMacIds, threeMacIds);
            }
        }

        #region PRIVATE_METHODS

        private void TryAddElementToDataBase<ElementType>
            (DbContext ctx, DbSet<ElementType> dbSet, ElementType element) where ElementType : class
        {
            try
            {
                dbSet.Add(element);
                ctx.SaveChanges();

                PrintSuccesAddingToDatabaseMessage(element);
            }
            catch(Exception error)
            {
                PrintFailedAddingToDatabaseMessage(element, error.Message);

                throw error;
            }
        }

        private void PrintSuccesAddingToDatabaseMessage<T>(T elementToBeAdd)
        {
            Console.WriteLine($"Added {typeof(T).Name} element to database:");

            foreach (var property in typeof(T).GetProperties())
            {
                Console.WriteLine($"{property.Name} -> {property.GetValue(elementToBeAdd)}");
            }

            Console.WriteLine("\n");
        }

        private void PrintFailedAddingToDatabaseMessage<T>(T elementToBeAdd, string failExceptionMessage)
        {
            Console.ForegroundColor = ConsoleColor.Red;

            Console.WriteLine(failExceptionMessage);
            Console.WriteLine($"When trying to add {typeof(T).Name} element to databse:");

            Console.WriteLine(typeof(T).Name);
            foreach (var property in typeof(T).GetProperties())
            {
                Console.WriteLine($"{property.Name} -> {property.GetValue(elementToBeAdd)}");
            }

            Console.WriteLine("\n");

            Console.ResetColor();
        }

        #endregion
    }
}
