import { ProductImage } from "./product.image";
export interface Product {
  id: number;
  name: string;
  price: number;
  thumbnail: string;
  description: string;
  category_id: number;
  url: string;
  product_images: ProductImage[];
  operating_system?: string; // Optional, based on backend field
  screen_size?: number; // BigDecimal should be converted to number in JS/TS
  battery_capacity?: number;
  ram?: number;
  rom?: number;
  front_camera?: string;
  main_camera?: string;
  color?: string;
  release_date?: number; // Year
  in_stock?: number;
  brand_id?: number;
}

  
  